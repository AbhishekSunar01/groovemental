package com.grooveMental.backend.dto;

import lombok.Data;

@Data
public class RegisterReqDto {
    private String username;
    private String email;
    private String password;
    private String role;
}
