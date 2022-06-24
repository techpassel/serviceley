package com.tp.serviceley.server.config;

import com.tp.serviceley.server.util.JwtRequestFilter;
import com.tp.serviceley.server.service.auth.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtRequestFilter jwtRequestFilter;

    /*
    In Spring security CORS must be processed before Spring Security because the pre-flight request will not
    contain any cookies (i.e. the JSESSIONID). And If the request does not contain any cookies and Spring Security
    is first, the request will determine the user is not authenticated (since there are no cookies in the request)
    and reject it. The easiest way to ensure that CORS is handled first is to use the Cors Filter. Users can
    integrate the CorsFilter with Spring Security by providing a CorsConfigurationSource as follows.
    */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors(Customizer.withDefaults())// by default, it uses a Bean by the name of corsConfigurationSource(defined above)
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll() //Don't validate these requests.
                .antMatchers("/api/admin/**").hasAnyAuthority("admin")
                .anyRequest().authenticated() //Validate all other requests
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //Here we are asking spring security not to manage session. Since we have asked spring security not to manage state.
        //So now we need something which will validate each request and sets up security context. So following code is doing the same.
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /*
    The UserDetailsService interface is used to retrieve user-related data. It has one method named loadUserByUsername()
    which can be overridden to customize the process of finding the user. It is used by the DaoAuthenticationProvider to
    load details about the user during authentication. If we create a customized UserDetailsService implementation we
    have to tell the spring security about that. We can achieve that by configuring AuthenticationManagerBuilder as follows.
    Note that in "auth.userDetailsService(userDetailsServiceImpl)", 'auth' represents an instance(i.e. object) of
    AuthenticationManagerBuilder class, userDetailsService() represents an instance method of the AuthenticationManagerBuilder.
    And 'userDetailsServiceImpl' represents an instance(i.e. object) of customized UserDetailsService class(we named the
    class as - UserDetailsServiceImpl and is presents in "com.tp.serviceley.server.service.auth" package.)
    */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl);
    }

    /*
    Note that here we are using authenticationManagerBean() method and not authenticationManager() method. Actually
    authenticationManager() method is used to do some configuration to the AuthenticationManager while
    authenticationManagerBean() method is used to expose the AuthenticationManager as a Spring Bean that can be
    Autowired and used. Like we have used object of AuthenticationManager in AuthService login() function to
    authenticate used details.
    */
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    /*
    Here we are creating a Spring bean of PasswordEncoder that can be autowired and used anywhere in the application.
    Like we have used it in AuthService signup() and resetPassword() method to encode the password.
    */
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}