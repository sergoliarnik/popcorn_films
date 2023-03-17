package com.example.popcorn_films.service;

import com.example.popcorn_films.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);
}
