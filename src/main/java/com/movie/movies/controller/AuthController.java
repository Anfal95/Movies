package com.movie.movies.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.movie.movies.dto.LoginDto;
import com.movie.movies.entity.User;
import com.movie.movies.exceptions.BaseException;
import com.movie.movies.service.CustomUserDetailsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3002")
public class AuthController {
	
//	@Autowired
//    private AuthService service;

//    @Autowired
//    private AuthenticationManager authenticationManager;

	private final CustomUserDetailsService customUserDetailsService;
    
	
    public AuthController(CustomUserDetailsService customUserDetailsService){
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3002")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto loginRequest) throws BaseException {

        return ResponseEntity.ok(this.customUserDetailsService.loadUserByUsername(loginRequest.getUsername()));
    }
    
    @PostMapping("/signup")
    @CrossOrigin(origins = "http://localhost:3002")
    public ResponseEntity<Object> signup(@Valid @RequestBody User userRequest) throws BaseException {
    	
        return ResponseEntity.ok(this.customUserDetailsService.signup(userRequest));
    }
    

    @GetMapping("/validate")
    public boolean validateCredentials(String[] credentials) {
    	
    	System.out.println("credential");
    	
    	return this.customUserDetailsService.validate(credentials);
    }
}
