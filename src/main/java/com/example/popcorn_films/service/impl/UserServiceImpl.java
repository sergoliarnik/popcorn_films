package com.example.popcorn_films.service.impl;

import com.example.popcorn_films.dto.UserDto;
import com.example.popcorn_films.entity.User;
import com.example.popcorn_films.exception.ResourceNotFoundException;
import com.example.popcorn_films.repository.UserRepo;
import com.example.popcorn_films.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final static String resourceName = "User";

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Override
    public UserDto createUser(UserDto userDto) {
        return mapToDto(userRepo.save(mapToEntity(userDto)));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream().map(this::mapToDto).toList();
    }

    @Override
    public UserDto getUserById(Long id) {
        return mapToDto(userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(resourceName, "id", String.valueOf(id))));
    }

    private UserDto mapToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setDescription(user.getDescription());
        userDto.setRole(user.getRole());
        userDto.setStatus(user.getStatus());
        return userDto;
    }

    private User mapToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setDescription(userDto.getDescription());
        user.setRole(userDto.getRole());
        user.setStatus(userDto.getStatus());
        return user;
    }
}
