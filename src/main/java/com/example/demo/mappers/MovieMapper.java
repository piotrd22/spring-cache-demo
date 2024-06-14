package com.example.demo.mappers;

import com.example.demo.abstractions.BaseMapper;
import com.example.demo.dto.read.MovieReadDto;
import com.example.demo.dto.write.MovieWriteDto;
import com.example.demo.models.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper implements BaseMapper<Movie, MovieReadDto, MovieWriteDto> {

    // Use builder pattern maybe?
    @Override
    public MovieReadDto toDto(Movie entity) {
        return new MovieReadDto(
                entity.getId(),
                entity.getDirector(),
                entity.getTitle(),
                entity.getDescription());
    }

    @Override
    public Movie toEntity(MovieWriteDto writeDto) {
        Movie movie = new Movie();
        movie.setDirector(writeDto.director());
        movie.setTitle(writeDto.title());
        movie.setDescription(writeDto.description());
        return movie;
    }
}
