package com.example.popcorn_films.controller;

import com.example.popcorn_films.constants.HttpStatuses;
import com.example.popcorn_films.dto.JwtAuthResponse;
import com.example.popcorn_films.dto.LoginDto;
import com.example.popcorn_films.dto.RegisterDto;
import com.example.popcorn_films.dto.UserDto;
import com.example.popcorn_films.entity.User;
import com.example.popcorn_films.service.AuthService;
import com.example.popcorn_films.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication Resource")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;
    private final UserService userService;


    @Operation(summary = "Login user")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        UserDto userDto = userService.findUserByEmail(loginDto.getEmail());

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setUserId(userDto.getId());
        jwtAuthResponse.setUserRole(userDto.getRole());

        return ResponseEntity.ok(jwtAuthResponse);
    }

    @Operation(summary = "Register user")
    @ApiResponse(responseCode = "201", description = HttpStatuses.CREATED)
    @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDto registerDto) {
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
