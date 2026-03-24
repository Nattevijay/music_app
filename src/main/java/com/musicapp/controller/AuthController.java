package com.musicapp.controller;

import com.musicapp.document.User;
import com.musicapp.dto.AuthRequest;
import com.musicapp.dto.AuthResponse;
import com.musicapp.dto.RegisterRequest;
import com.musicapp.service.AppUserDetailsService;
import com.musicapp.service.UserService;
import com.musicapp.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {

            //authenticate the user
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            //Load the user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
            User user = userService.findByEmail(request.getEmail());

//            Generate Jwt Token
            String token = jwtUtil.generateToken(userDetails,user.getRole().name());


            return ResponseEntity.ok(new AuthResponse(token, request.getEmail(), user.getRole().name()));
        } catch (BadCredentialsException e) {
            log.error(" invalid login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Credentials: " + e.getMessage());
        }catch (Exception e) {
            log.error("Error during login: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed: " + e.getMessage());
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            log.info("User registered successfully with email: {}", request.getEmail());
            return ResponseEntity.ok(userService.registerUser(request));
        } catch (RuntimeException e) {
            log.error("Error during registration: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error during registration: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }
}
