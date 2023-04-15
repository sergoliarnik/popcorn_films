package com.example.popcorn_films.service;

import com.example.popcorn_films.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto findUserById(Long id);

    UserDto findUserByEmail(String email);
}
