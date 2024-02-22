package com.movie.movies.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SpringSecurity {
	
	@Autowired
    private UserDetailsService userDetailsService;

	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	
    	http
        .authorizeHttpRequests((authorize) ->
                authorize
                .requestMatchers(HttpMethod.POST , "/api/auth/**").permitAll()
                .requestMatchers("/api/**").permitAll()
                

        ).csrf()
        .disable().cors().disable();

    	return http.build();
//        http
//        .authorizeHttpRequests((authorize) ->
//                        authorize
//                        .requestMatchers("/register").permitAll()
//                        .requestMatchers("/api/**").permitAll()
//                        .requestMatchers("/api/save").permitAll()
//                        .requestMatchers("/api/login").permitAll()
//                                .requestMatchers("/index").permitAll()
////                                .requestMatchers("/api/**").permitAll()
////                                .requestMatchers("/api/movies/addToFavorits/**").permitAll()
////                                .requestMatchers("/api/movies/**").permitAll());
//                ).formLogin(
//                        form -> form
//                                .loginPage("/api/login")
//                                .permitAll()
//                ).logout(
//                        logout -> logout
//                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                                .permitAll()
//                );
//        return http.build();
    	
    	
    }

}
