package com.example.popcorn_films.service.impl;

import com.example.popcorn_films.dto.LoginDto;
import com.example.popcorn_films.dto.RegisterDto;
import com.example.popcorn_films.entity.User;
import com.example.popcorn_films.exception.ResourceAlreadyExistsException;
import com.example.popcorn_films.repository.UserRepo;
import com.example.popcorn_films.security.JwtTokenProvider;
import com.example.popcorn_films.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final static String resourceName = "User";
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;
    private final JwtTokenProvider tokenProvider;

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return tokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDto registerDto) {
        if(userRepo.existsByEmail(registerDto.getEmail())){
            throw new ResourceAlreadyExistsException(resourceName);
        }
        User user = mapper.map(registerDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        return "User registered successfully!";
    }
}
