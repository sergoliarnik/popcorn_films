package com.example.popcorn_films.service;

import com.example.popcorn_films.dto.UpdateUserDto;
import com.example.popcorn_films.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto findUserById(Long id);

    UserDto findUserByEmail(String email);

    List<UserDto> findAllUsers();

    UserDto updateUser(UpdateUserDto updateUserDto, String email);
    void deleteCurrentUser(String email);

    void deleteUserById(Long id);
}
