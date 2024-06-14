package com.example.demo.abstractions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

// Just for demo, it is not needed in this app
public abstract class AbstractControllerBase {

    public URI getURILocationFromRequest(Long id, HttpServletRequest request) {
        return ServletUriComponentsBuilder
                .fromRequest(request)
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
