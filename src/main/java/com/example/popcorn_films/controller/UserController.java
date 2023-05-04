package com.example.popcorn_films.controller;

import com.example.popcorn_films.constants.HttpStatuses;
import com.example.popcorn_films.dto.UpdateUserDto;
import com.example.popcorn_films.dto.UserDto;
import com.example.popcorn_films.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "User Resource")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
@CrossOrigin
public class UserController {
    private final UserService userService;

    @Operation(summary = "Find all users")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @Operation(summary = "Find user by id")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

    @Operation(summary = "Update user")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'USER')")
    @PutMapping
    public ResponseEntity<UserDto> update(@RequestBody @Valid UpdateUserDto updateUserDto, Principal principal) {
        return new ResponseEntity<>(userService.updateUser(updateUserDto, principal.getName()), HttpStatus.OK);
    }

    @Operation(summary = "Delete user by id")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Delete current user")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'USER')")
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteCurrentUser(Principal principal) {
        userService.deleteCurrentUser(principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
