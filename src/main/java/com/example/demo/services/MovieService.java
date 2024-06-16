package com.example.demo.services;

import com.example.demo.dto.read.MovieReadDto;
import com.example.demo.exceptions.NotFoundException;
import com.example.demo.exceptions.SavingEntityException;
import com.example.demo.models.Movie;
import com.example.demo.repositories.MovieRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// https://github.com/bezkoder/spring-boot-redis-example/tree/master
// https://stackoverflow.com/questions/51237672/spring-cache-all-elements-in-list-separately

// This example illustrates caching practices. Depending on your project's requirements,
// you may choose to apply different caching strategies. For instance, avoiding caching for findAll might be recommended.
// Nice idea is also to cache responses from external services.
@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Transactional
    @Cacheable(value = "allMovieReadDto")
    public List<MovieReadDto> findAll() {
        return movieRepository.findAllProjectedBy(MovieReadDto.class);
    }

    // If we have some filter criteria, we can add its toString() representation to the key and concatenate the string with pageable
    @Transactional
    @Cacheable(value = "pageMovieReadDto", key = "#pageable?.toString()")
    public Page<MovieReadDto> findPage(Pageable pageable) {
        return movieRepository.findAllProjectedBy(pageable, MovieReadDto.class);
    }

    @Transactional
    @Cacheable(value = "movieReadDto", key = "#id")
    public MovieReadDto findById(Long id) {
        return movieRepository.findById(id, MovieReadDto.class).orElseThrow(NotFoundException::new);
    }

    @Transactional
    @CacheEvict(value = { "allMovieReadDto", "pageMovieReadDto" }, allEntries = true)
    public Movie create(Movie movie) {
        return save(movie);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "movieReadDto", key = "#result.id"),
            @CacheEvict(value = { "allMovieReadDto", "pageMovieReadDto" }, allEntries = true)
    })
    public Movie update(Long id, Movie movieNewData) {
        Movie movie = movieRepository.findById(id).orElseThrow(NotFoundException::new);
        movie.update(movieNewData);
        return save(movie);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "movieReadDto", key = "#id"),
            @CacheEvict(value = { "allMovieReadDto", "pageMovieReadDto" }, allEntries = true)
    })
    public void delete(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(NotFoundException::new);
        movieRepository.delete(movie);
    }

    private Movie save(Movie movie) {
        try {
            return movieRepository.save(movie);
        } catch (Exception _) {
            throw new SavingEntityException();
        }
    }
}
