package com.example.popcorn_films.service.impl;

import com.example.popcorn_films.constants.Resources;
import com.example.popcorn_films.dto.FilmDto;
import com.example.popcorn_films.entity.Film;
import com.example.popcorn_films.exception.ResourceAlreadyExistsException;
import com.example.popcorn_films.exception.ResourceNotFoundException;
import com.example.popcorn_films.repository.FilmRepo;
import com.example.popcorn_films.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmRepo filmRepo;
    private final ModelMapper mapper;

    @Override
    public FilmDto saveFilm(FilmDto filmDto) {
        if (filmRepo.existsByApiTitleId(filmDto.getApiTitleId())) {
            throw new ResourceAlreadyExistsException(Resources.FILM);
        }
        Film film = mapper.map(filmDto, Film.class);
        return mapper.map(filmRepo.save(film), FilmDto.class);
    }

    @Override
    public List<FilmDto> saveAllOnlyNewFilms(List<FilmDto> filmDtos) {
        List<Film> onlyNewFilms = filmDtos.stream()
                .map(filmDto -> mapper.map(filmDto, Film.class))
                .toList();

        return filmRepo.saveAll(onlyNewFilms).stream()
                .map(film -> mapper.map(film, FilmDto.class))
                .toList();
    }

    @Override
    public List<FilmDto> findAllFilms() {
        return filmRepo.findAll().stream().map(film -> mapper.map(film, FilmDto.class)).toList();
    }

    @Override
    public FilmDto findFilmById(Long id) {
        Film film = filmRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Resources.FILM, "id", String.valueOf(id)));
        return mapper.map(film, FilmDto.class);
    }

    @Override
    public FilmDto updateFilm(FilmDto filmDto) {
        Film film = filmRepo.findById(filmDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException(Resources.FILM, "id", String.valueOf(filmDto.getId())));
        film.setApiTitleId(filmDto.getApiTitleId());
        return mapper.map(film, FilmDto.class);
    }

    @Override
    public void deleteFilmById(Long id) {
        Film film = filmRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(Resources.FILM, "id", String.valueOf(id)));
        filmRepo.delete(film);
    }
}