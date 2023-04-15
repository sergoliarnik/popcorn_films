package com.example.popcorn_films.service.impl;

import com.example.popcorn_films.dto.LoginDto;
import com.example.popcorn_films.dto.RegisterDto;
import com.example.popcorn_films.entity.User;
import com.example.popcorn_films.exception.BadRequestException;
import com.example.popcorn_films.repository.UserRepo;
import com.example.popcorn_films.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepo userRepo, PasswordEncoder passwordEncoder, ModelMapper mapper) {
        this.authenticationManager = authenticationManager;
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "User login successfully!";
    }

    @Override
    public String register(RegisterDto registerDto) {
        if(userRepo.existsByEmail(registerDto.getEmail())){
            throw new BadRequestException("User already exists");
        }
        User user = mapper.map(registerDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        return null;
    }
}
