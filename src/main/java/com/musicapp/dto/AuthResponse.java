package com.musicapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthResponse {

//    private Boolean success;
//    private String message;
    private String token;
    private String email;
    private String role;

//    public static AuthResponse success(String token, String email, String role) {
//        return AuthResponse.builder()
//                .success(true)
//                .message("Authentication successful")
//                .token(token)
//                .email(email)
//                .role(role)
//                .build();
//    }
//
//    public static AuthResponse error(String message) {
//        return AuthResponse.builder()
//                .success(false)
//                .message(message)
//                .email(null)
//                .role(null)
//                .token(null)
//                .build();
//    }
}
