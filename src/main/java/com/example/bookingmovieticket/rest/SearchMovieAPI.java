package com.example.bookingmovieticket.rest;

import com.example.bookingmovieticket.entity.Movie;
import com.example.bookingmovieticket.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/search-movie")
@RequiredArgsConstructor
public class SearchMovieAPI {
    private final MovieService movieService;
    @GetMapping()
    public List<Movie> searchMovies(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String keyword
    ) {
        return movieService.searchMovie(genre, year, country, keyword);
    }

}
