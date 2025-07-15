package com.grooveMental.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResDto {
    private String username;
    private String role;
    private String accessToken;
    private String refreshToken;
}