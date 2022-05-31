package com.tp.serviceley.server.util;

import com.tp.serviceley.server.model.redis.RedisSession;
import com.tp.serviceley.server.service.auth.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${app.security.jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        String jwt = null;
        String username = null;
        RedisSession session = null;

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String sessionKey = authHeader.substring(7);
            if(redisUtil.hasKey(sessionKey) && redisUtil.expire(sessionKey, jwtExpirationInMillis)){
                session = redisUtil.get(sessionKey);
                jwt = session.getToken();
            }
            Long userId = jwtProvider.extractUserId(jwt);
            String email = jwtProvider.extractUsername(jwt);
            if(userId == session.getUserId() && email.equals(session.getEmail())) username = email;
        }

        if(username !=null){
            //To get the user details from the username fetched in previous step.
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
            //To check if given token is valid and not expired.
            boolean isTokenValid = jwtProvider.validateToken(jwt, userDetails);
            //For further processing of request if token is valid.
            if(isTokenValid){
                //Here we are manually creating "UsernamePasswordAuthenticationToken" after validating token so that spring security can verify it allow for further processing.
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                //Actually above three process is what spring security would have done by its own.
                //But now since we have modified security context hence we need to reconfigure it.
            }
        }
        filterChain.doFilter(request, response);
    }
}
