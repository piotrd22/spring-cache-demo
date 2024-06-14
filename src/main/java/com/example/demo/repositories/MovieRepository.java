package com.example.demo.repositories;

import com.example.demo.abstractions.ReadDto;
import com.example.demo.models.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// https://vladmihalcea.com/spring-jpa-dto-projection/
// https://thorben-janssen.com/spring-data-jpa-query-projections/
// https://stackoverflow.com/questions/50879431/spring-jpa-projection-findall
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    <T extends ReadDto> Optional<T> findById(Long id, Class<T> clazz);
    <T extends ReadDto> List<T> findAllProjectedBy(Class<T> clazz);
    <T extends ReadDto> Page<T> findAllProjectedBy(Pageable pageable, Class<T> clazz);
}
