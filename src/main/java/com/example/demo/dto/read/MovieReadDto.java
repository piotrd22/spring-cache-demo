package com.example.demo.dto.read;

import com.example.demo.abstractions.ReadDto;

public record MovieReadDto(
        Long id,
        String director,
        String title,
        String description
) implements ReadDto {
}
