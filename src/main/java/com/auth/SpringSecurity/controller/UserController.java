package com.auth.SpringSecurity.controller;

import com.auth.SpringSecurity.model.Users;
import com.auth.SpringSecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        Map<String, Object> response = new HashMap<>();
        try {
            userService.addUser(user);
            response.put("message", "User Registered Successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");
        Map<String, Object> response = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // Example token, replace with actual token generation logic
            String token = "some-jwt-token"; // Generate JWT token based on userDetails
            response.put("message", "Login Successful");
            response.put("token", token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AuthenticationException e) {
            response.put("message", "Invalid Credentials");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
