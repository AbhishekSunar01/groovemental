package com.grooveMental.backend.controller;

import com.grooveMental.backend.dto.*;
import com.grooveMental.backend.model.User;
import com.grooveMental.backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<RegisterResDto>> register(@RequestBody RegisterReqDto registerReqDto) {
        User user = authService.register(registerReqDto);

        RegisterResDto response = new RegisterResDto();
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());

        return new ResponseEntity<>(
                new ApiResponseDto<>(true, "User registered successfully"),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<LoginResDto>> login(@RequestBody LoginReqDto loginReqDto) {
        LoginResDto response = authService.login(loginReqDto);
        return ResponseEntity.ok(new ApiResponseDto<>(true, "Login successfully", response));
    }

}
