package com.example.popcorn_films.service.impl;

import com.example.popcorn_films.constants.ErrorMessages;
import com.example.popcorn_films.constants.Resources;
import com.example.popcorn_films.dto.UpdateUserDto;
import com.example.popcorn_films.dto.UserDto;
import com.example.popcorn_films.entity.User;
import com.example.popcorn_films.enums.UserRole;
import com.example.popcorn_films.exception.ResourceNotFoundException;
import com.example.popcorn_films.repository.UserRepo;
import com.example.popcorn_films.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;



    @Override
    public UserDto findUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "id", String.valueOf(id)));

        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "email", email));
        return mapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> findAllUsers() {
        return userRepo.findAll().stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public UserDto updateUser(UpdateUserDto updateUserDto, String email) {
        User currentUser = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "email", email));

        if (!currentUser.getRole().equals(UserRole.ADMIN)
                && !updateUserDto.getId().equals(currentUser.getId())) {
            throw new AccessDeniedException(ErrorMessages.ACCESS_DENIED);
        }

        currentUser.setName(updateUserDto.getName());
        currentUser.setSurname(updateUserDto.getSurname());
        currentUser.setDescription(updateUserDto.getDescription());
        currentUser.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
        currentUser.setEmail(updateUserDto.getEmail());

        return mapper.map(currentUser, UserDto.class);
    }

    @Override
    @Transactional
    public void deleteCurrentUser(String email) {
        User currentUser = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "email", email));
        userRepo.delete(currentUser);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        User currentUser = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "id", String.valueOf(id)));
        userRepo.delete(currentUser);
    }
}
