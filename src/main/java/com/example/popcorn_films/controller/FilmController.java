package com.example.popcorn_films.controller;

import com.example.popcorn_films.constants.HttpStatuses;
import com.example.popcorn_films.dto.FilmDto;
import com.example.popcorn_films.enums.SavedFilmStatus;
import com.example.popcorn_films.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Tag(name = "Film Resource")
@RequiredArgsConstructor
@RestController
@RequestMapping("api/films")
@CrossOrigin
public class FilmController {
    private final FilmService filmService;

    @Operation(summary = "Save new film")
    @ApiResponse(responseCode = "201", description = HttpStatuses.CREATED)
    @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    ResponseEntity<FilmDto> save(@RequestBody @Valid FilmDto filmDto) {
        return new ResponseEntity<>(filmService.saveFilm(filmDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Save all only new films")
    @ApiResponse(responseCode = "201", description = HttpStatuses.CREATED)
    @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/all")
    ResponseEntity<List<FilmDto>> saveAllOnlyNew(@RequestBody @Valid List<FilmDto> filmDto) {
        return new ResponseEntity<>(filmService.saveAllOnlyNewFilms(filmDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Find all films")
    @ApiResponse(responseCode = "200", description = HttpStatuses.CREATED)
    @GetMapping
    ResponseEntity<List<FilmDto>> findAll() {
        return new ResponseEntity<>(filmService.findAllFilms(), HttpStatus.OK);
    }

    @Operation(summary = "Find film by id")
    @ApiResponse(responseCode = "200", description = HttpStatuses.CREATED)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @GetMapping("/{id}")
    ResponseEntity<FilmDto> findById(@PathVariable Long id) {
        return new ResponseEntity<>(filmService.findFilmById(id), HttpStatus.OK);
    }

    @Operation(summary = "Update film")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "400", description = HttpStatuses.BAD_REQUEST)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping
    ResponseEntity<FilmDto> update(@RequestBody @Valid FilmDto filmDto) {
        return new ResponseEntity<>(filmService.updateFilm(filmDto), HttpStatus.OK);
    }

    @Operation(summary = "Delete film by id")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        filmService.deleteFilmById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Add to saved film")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'USER')")
    @PostMapping("/{filmApiId}/add-to-saved")
    ResponseEntity<HttpStatus> addToSaved(@PathVariable String filmApiId, @RequestParam SavedFilmStatus status,
                                          Principal principal) {
        filmService.addToSaved(filmApiId, status, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Remove from saved film")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'USER')")
    @DeleteMapping("/{filmApiId}/remove-from-saved")
    ResponseEntity<HttpStatus> removeFromSaved(@PathVariable String filmApiId, @RequestParam SavedFilmStatus status,
                                               Principal principal) {
        filmService.removeFromSaved(filmApiId, status, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get saved film")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'USER')")
    @GetMapping("/get-saved")
    ResponseEntity<List<FilmDto>> getSaved(@RequestParam SavedFilmStatus status,
                                           Principal principal) {

        return new ResponseEntity<>(filmService.getSaves(status, principal.getName()), HttpStatus.OK);
    }

    @Operation(summary = "Rate film")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @SecurityRequirement(name = "Bearer Authentication")
    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR', 'USER')")
    @GetMapping("/{id}/rate")
    ResponseEntity<HttpStatus> rate(@PathVariable Long id, @RequestParam @Min(1) @Max(10) Long mark,
                                    Principal principal) {
        filmService.rate(id, mark, principal.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get film rating")
    @ApiResponse(responseCode = "200", description = HttpStatuses.OK)
    @ApiResponse(responseCode = "404", description = HttpStatuses.NOT_FOUND)
    @GetMapping("/{id}/rating")
    ResponseEntity<Double> getRating(@PathVariable Long id) {
        return new ResponseEntity<>(filmService.getRating(id), HttpStatus.OK);
    }
}
