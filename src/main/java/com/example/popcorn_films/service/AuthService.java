package com.example.popcorn_films.service;

import com.example.popcorn_films.dto.LoginDto;
import com.example.popcorn_films.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
