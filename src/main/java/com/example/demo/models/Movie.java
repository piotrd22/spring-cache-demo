package com.example.demo.models;

import com.example.demo.abstractions.AbstractEntityBase;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Movie extends AbstractEntityBase<Movie> {
    private String title;
    private String description;
    private String director;

    @Override
    public void update(Movie movie) {
        if (movie.title != null) {
            title = movie.title;
        }
        if (movie.description != null) {
            description = movie.description;
        }
        if (movie.director != null) {
            director = movie.director;
        }
    }
}
