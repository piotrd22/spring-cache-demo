package com.example.demo.dto.write;

import com.example.demo.abstractions.WriteDto;
import jakarta.validation.constraints.NotBlank;

public record MovieWriteDto(
        @NotBlank(message = "Director cannot be blank")
        String director,

        @NotBlank(message = "Title cannot be blank")
        String title,

        @NotBlank(message = "Description cannot be blank")
        String description
) implements WriteDto {
}
