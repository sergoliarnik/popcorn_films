package com.example.popcorn_films.service.impl;

import com.example.popcorn_films.dto.UserDto;
import com.example.popcorn_films.entity.User;
import com.example.popcorn_films.exception.ResourceNotFoundException;
import com.example.popcorn_films.repository.UserRepo;
import com.example.popcorn_films.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ModelMapper mapper;
    private final static String resourceName = "User";

    @Override
    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream()
                .map(user -> mapper.map(user, UserDto.class))
                .toList();
    }

    @Override
    public UserDto findUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", String.valueOf(id)));

        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return mapper.map(user, UserDto.class);
    }
}
