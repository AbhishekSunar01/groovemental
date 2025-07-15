package com.grooveMental.backend.service;

import com.grooveMental.backend.dto.LoginReqDto;
import com.grooveMental.backend.dto.LoginResDto;
import com.grooveMental.backend.dto.RegisterReqDto;
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

    UserRepo userRepo;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    AuthenticationManager authenticationManager;
    JwtService jwtService;

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

    public User register(RegisterReqDto registerReqDto) {
        User user = new User();
        user.setUsername(registerReqDto.getUsername());
        user.setEmail(registerReqDto.getEmail());
        user.setRole(registerReqDto.getRole());
        user.setPassword(bCryptPasswordEncoder.encode(registerReqDto.getPassword()));
        return userRepo.save(user);
    }

    public LoginResDto login(LoginReqDto loginReqDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginReqDto.getUsername(), loginReqDto.getPassword()
                )
        );

        if (authentication.isAuthenticated()) {
            User user = userRepo.findByUsername(loginReqDto.getUsername());
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            return new LoginResDto(user.getUsername(), user.getRole(), accessToken, refreshToken);
        } else {
            throw new RuntimeException("Invalid username or password");
        }
    }


}
