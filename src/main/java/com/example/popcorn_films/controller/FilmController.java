package com.example.popcorn_films.controller;

import com.example.popcorn_films.constants.HttpStatuses;
import com.example.popcorn_films.dto.FilmDto;
import com.example.popcorn_films.service.FilmService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Film Resource")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/films")
public class FilmController {
    private final FilmService filmService;

    @ApiResponse(responseCode = "201", description = HttpStatuses.CREATED)
    @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    ResponseEntity<FilmDto> save(@RequestBody @Valid FilmDto filmDto) {
        return new ResponseEntity<>(filmService.saveFilm(filmDto), HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "201", description = HttpStatuses.CREATED)
    @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/all")
    ResponseEntity<List<FilmDto>> saveAllOnlyNew(@RequestBody @Valid List<FilmDto> filmDto) {
        return new ResponseEntity<>(filmService.saveAllOnlyNewFilms(filmDto), HttpStatus.CREATED);
    }

    @ApiResponse(responseCode = "200", description = HttpStatuses.CREATED)
    @GetMapping
    ResponseEntity<List<FilmDto>> findAll() {
        return new ResponseEntity<>(filmService.findAllFilms(), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = HttpStatuses.CREATED)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @GetMapping("/{id}")
    ResponseEntity<FilmDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(filmService.findFilmById(id), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping
    ResponseEntity<FilmDto> update(@RequestBody @Valid FilmDto filmDto) {
        return new ResponseEntity<>(filmService.updateFilm(filmDto), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        filmService.deleteFilmById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
