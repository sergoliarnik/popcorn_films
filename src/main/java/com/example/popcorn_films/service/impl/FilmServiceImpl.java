package com.example.popcorn_films.service.impl;

import com.example.popcorn_films.constants.Resources;
import com.example.popcorn_films.dto.FilmDto;
import com.example.popcorn_films.entity.Film;
import com.example.popcorn_films.entity.Rating;
import com.example.popcorn_films.entity.SavedFilm;
import com.example.popcorn_films.entity.User;
import com.example.popcorn_films.enums.SavedFilmStatus;
import com.example.popcorn_films.exception.ResourceAlreadyExistsException;
import com.example.popcorn_films.exception.ResourceNotFoundException;
import com.example.popcorn_films.repository.FilmRepo;
import com.example.popcorn_films.repository.RatingRepo;
import com.example.popcorn_films.repository.SavedFilmRepo;
import com.example.popcorn_films.repository.UserRepo;
import com.example.popcorn_films.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmServiceImpl implements FilmService {

    private final FilmRepo filmRepo;
    private final UserRepo userRepo;
    private final SavedFilmRepo savedFilmRepo;
    private final ModelMapper mapper;
    private final RatingRepo ratingRepo;

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

    @Override
    @Transactional
    public void addToSaved(String filmApiId, SavedFilmStatus status, String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "email", userEmail));

        Film film = filmRepo.findByApiTitleId(filmApiId).orElse(filmRepo.save(Film.builder().apiTitleId(filmApiId).build()));

        if (savedFilmRepo.existsByFilmIdAndStatus(film.getId(), status)) {
            throw new ResourceAlreadyExistsException(Resources.SAVED_FILM);
        }

        SavedFilm savedFilm = SavedFilm.builder()
                .user(user)
                .film(film)
                .status(status)
                .build();

        savedFilmRepo.save(savedFilm);
    }

    @Override
    public void removeFromSaved(String filmApiId, SavedFilmStatus status, String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "email", userEmail));

        if (!filmRepo.existsByApiTitleId(filmApiId)) {
            throw new ResourceNotFoundException(Resources.FILM, "filmApiId", String.valueOf(filmApiId));
        }

        SavedFilm savedFilm = savedFilmRepo.findByUserIdAndFilmApiTitleIdAndStatus(user.getId(), filmApiId, status).orElseThrow(
                () -> new ResourceNotFoundException(Resources.SAVED_FILM));

        savedFilmRepo.delete(savedFilm);
    }

    @Override
    public List<FilmDto> getSaves(SavedFilmStatus status, String userEmail) {
        User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "email", userEmail));

        List<SavedFilm> savedFilms = savedFilmRepo.findByUserIdAndStatus(user.getId(), status);

        return savedFilms.stream()
                .map(savedFilm -> mapper.map(savedFilm.getFilm(), FilmDto.class))
                .toList();
    }

    @Override
    public void rate(Long filmId, Long mark, String userEmail) {
        User user = userRepo.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException(Resources.USER, "email", userEmail));

        Film film = filmRepo.findById(filmId).orElseThrow(
                () -> new ResourceNotFoundException(Resources.FILM, "id", String.valueOf(filmId)));

        if (ratingRepo.existsByUserIdAndFilmId(user.getId(), film.getId())) {
            throw new ResourceAlreadyExistsException(Resources.RATING);
        }

        Rating rating = Rating.builder()
                .user(user)
                .film(film)
                .mark(mark)
                .build();

        ratingRepo.save(rating);
    }

    @Override
    public Double getRating(Long filmId) {
        if (!filmRepo.existsById(filmId)) {
            throw new ResourceNotFoundException(Resources.FILM, "id", String.valueOf(filmId));
        }

        if (!ratingRepo.existsByFilmId(filmId)) {
            throw new ResourceNotFoundException(Resources.RATING);
        }

        return ratingRepo.findAvgMarkByFilmId(filmId);
    }
}
