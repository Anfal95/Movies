package com.movie.movies.service;

import java.util.Collections;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.movie.movies.entity.User;
import com.movie.movies.exceptions.BaseException;
import com.movie.movies.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    
    public org.springframework.security.core.context.SecurityContext context = SecurityContextHolder.createEmptyContext();
    
    public Authentication authentication;

    public CustomUserDetailsService(UserRepository userRepository, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.userRepository = userRepository;
        
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        
    }

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
    	
          User user = userRepository.findByUsername(usernameOrEmail)
                 .orElseThrow(() ->
                         new UsernameNotFoundException("User not found with username or email: "+ usernameOrEmail));
          
           
          Authentication authentication =
              new TestingAuthenticationToken(usernameOrEmail, user.getPassword(), "empty");
          
          context.setAuthentication(authentication);
          

          SecurityContextHolder.setContext(context);

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),
                Collections.<GrantedAuthority>emptySet());
    }
    
    
    public User signup(User userRequest) {
    	
    		User userExist = userRepository.findByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail());
    		
    		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    		String encryptedPassword = encoder.encode(userRequest.getPassword());
    		
    		if(userExist != null) {
    			throw new BaseException("User Already Exist","USER_ALREADY_EXIST");
    		}

            User newUser = new User(userRequest.getEmail(),userRequest.getFirstName(), userRequest.getLastName(),
            		userRequest.getUsername(), encryptedPassword);
            
            userRepository.save(newUser);
            
            return newUser;

    }

	public boolean validate(String[] credentials) {
		
		User user = userRepository.findByUsername(credentials[0])
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: "+ credentials[0]));
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if (credentials[0] != null && encoder.matches(credentials[0], user.getPassword())) {
		    return true;
		}
		
		return false;
	}
}