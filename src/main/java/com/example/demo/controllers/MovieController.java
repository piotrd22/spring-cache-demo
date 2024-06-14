package com.example.demo.controllers;

import com.example.demo.abstractions.AbstractControllerBase;
import com.example.demo.dto.read.MovieReadDto;
import com.example.demo.dto.write.MovieWriteDto;
import com.example.demo.mappers.MovieMapper;
import com.example.demo.models.Movie;
import com.example.demo.services.MovieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/movies")
public class MovieController extends AbstractControllerBase {

    private final MovieService movieService;
    private final MovieMapper movieMapper;

    public MovieController(MovieService movieService, MovieMapper movieMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
    }

    @GetMapping
    public ResponseEntity<List<MovieReadDto>> getAllMovies() {
        log.info("Inside: MovieController -> getAllMovies()...");

        return ResponseEntity.ok(movieService.findAll());
    }

    @GetMapping("/page")
    public ResponseEntity<Page<MovieReadDto>> getPageOfMovies(@PageableDefault(size = 15) Pageable pageable) {
        log.info("Inside: MovieController -> getPageOfMovies()...");

        return ResponseEntity.ok(movieService.findPage(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieReadDto> getMovieById(@PathVariable Long id) {
        log.info("Inside: MovieController -> getMovieById()...");

        return ResponseEntity.ok(movieService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MovieReadDto> createMovie(@RequestBody @Valid MovieWriteDto movieWriteDto, HttpServletRequest request) {
        log.info("Inside: MovieController -> createMovie()...");

        Movie movie = movieMapper.toEntity(movieWriteDto);
        movie = movieService.create(movie);
        URI location = getURILocationFromRequest(movie.getId(), request);

        return ResponseEntity.created(location).body(movieMapper.toDto(movie));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieReadDto> updateMovie(@PathVariable Long id, @RequestBody @Valid MovieWriteDto movieWriteDto) {
        log.info("Inside: MovieController -> updateMovie()...");

        Movie movie = movieMapper.toEntity(movieWriteDto);
        movie = movieService.update(id, movie);

        return ResponseEntity.ok(movieMapper.toDto(movie));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        log.info("Inside: MovieController -> deleteMovie()...");

        movieService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
