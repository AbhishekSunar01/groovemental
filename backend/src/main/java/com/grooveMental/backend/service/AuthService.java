package com.grooveMental.backend.service;

import com.grooveMental.backend.dto.LoginRequestDto;
import com.grooveMental.backend.dto.LoginResponseDto;
import com.grooveMental.backend.dto.RegisterRequestDto;
import com.grooveMental.backend.model.User;
import com.grooveMental.backend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    MyUserDetailsService myUserDetailsService;
    public AuthService(
            UserRepo userRepo,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            AuthenticationManager authenticationManager,
            JwtService jwtService
            ) {
        this.userRepo = userRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public User register(RegisterRequestDto registerRequestDto) {
        User user = new User();
        user.setUsername(registerRequestDto.getUsername());
        user.setEmail(registerRequestDto.getEmail());
        user.setRole(registerRequestDto.getRole());
        user.setPassword(bCryptPasswordEncoder.encode(registerRequestDto.getPassword()));
        return userRepo.save(user);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDto.getUsername(), loginRequestDto.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            User user = userRepo.findByUsername(loginRequestDto.getUsername());
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            return new LoginResponseDto(user.getUsername(), user.getRole(), accessToken, refreshToken);
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }


}
