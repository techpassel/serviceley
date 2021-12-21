package com.tp.serviceley.server.security;
import com.tp.serviceley.server.exception.BackendException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtProvider {
    private KeyStore keyStore;

    @Value("${app.security.jwt.keystore-location}")
    private String keyStorePath;

    @Value("${app.security.jwt.keystore-password}")
    private String keyStorePassword;

    @Value("${app.security.jwt.key-alias}")
    private String keyAlias;

    @Value("${app.security.jwt.private-key-passphrase}")
    private String privateKeyPassphrase;

    @Value("${app.security.jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    /*
        keystore command format :-
        ---------------------------------------------------------------------------------------------------
        keytool -genkey -alias <alias> -keyalg RSA -keystore <keystore_name>  -keysize 2048
        ---------------------------------------------------------------------------------------------------
        where,
        alias — A name to uniquely identify the generated keypair entry within the generate keystore. Let’s use “jwtsigning”
        keyalg — Public key algorithm. Let's choose “RSA”.
        keystore — A name for the keystore file generated. Let’s use “keystore.jks”
        keysize — Size (a measure of strength) of the generated public key. We should set that to 2048 at least. Can be set to 4096 for better security (to further reduce the possibility of an attacker guessing your keys).

        So our final command to generate keystore file would be :
        ***************************************************************************************************
        keytool -genkey -alias jwtsigning -keyalg RSA -keystore keystore.jks  -keysize 2048
        ***************************************************************************************************
        Now, you will see a keystore.jks file created in your current directory. Copy that to the following directory.
        -----------------------------------------------
        src/main/resources/keys/keystore.jks
        -----------------------------------------------
     */

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(keyStorePath);
            keyStore.load(resourceAsStream, keyStorePassword.toCharArray());
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new BackendException("Exception occurred while loading keystore", e);
        }
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(getPrivateKey())
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationInMillis)))
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey(keyAlias, privateKeyPassphrase.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException  | UnrecoverableKeyException e) {
            System.out.println(e.getMessage()+"1111111111111*****************");
            throw new BackendException("Exception occurred while retrieving private key from keystore", e);
        }
    }

    private PublicKey getPublickey() {
        try {
            return keyStore.getCertificate(keyAlias).getPublicKey();
        } catch (KeyStoreException e) {
            System.out.println(e.getMessage()+"*****");
            throw new BackendException("Exception occurred while retrieving public key from keystore", e);
        }
    }

    //To validate token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    //Common function to retrive any particular data from token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //Function to retrieve all information from token
    private Claims getAllClaimsFromToken(String token) {
        try{
            return Jwts.parser().setSigningKey(getPublickey()).parseClaimsJws(token).getBody();
            //Handle exceptions as per your requirement.
        } catch (ExpiredJwtException e) {
            System.out.println("Token expired ");
        } catch(Exception e){
            System.out.println("Some other exception in JWT parsing ");
        }
        return null;
    }

    //To extract the username from token
    public String extractUsername(String token){
        try{
            return extractClaim(token, Claims::getSubject);
        } catch(NullPointerException e) {
            return null;
        }
    }

    //Check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    //To extract the expiration time of token
    private Date extractExpiration(String token) {
        try{
            return extractClaim(token, Claims::getExpiration);
        } catch(NullPointerException e) {
            return null;
        }
    }
}
