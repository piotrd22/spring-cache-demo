package com.example.demo.abstractions;

public interface BaseMapper<E extends AbstractEntityBase, R extends ReadDto, W extends WriteDto> {
    R toDto(E entity);
    E toEntity(W writeDto);
}
