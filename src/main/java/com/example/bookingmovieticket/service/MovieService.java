package com.example.bookingmovieticket.service;
import com.example.bookingmovieticket.entity.Genre;
import com.example.bookingmovieticket.entity.Movie;
import com.example.bookingmovieticket.repository.MovieRepository;
import com.example.bookingmovieticket.spec.MovieSpecifications;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;


    public  List<Movie> getMovies(Boolean status){
        return movieRepository.findAllByStatus(status);
    }
    public Page<Movie> getMovies(Boolean status, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("publishedAt").descending()); // page trong jpa bắt đầu từ 0
        return movieRepository.findByStatus(status, pageRequest);
    }
    public Page<Movie> getMoviesShowing(Boolean status, Integer page, Integer size) {
        Date currentDate = new Date(); // Ngày hiện tại
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("publishedAt").descending());
        return movieRepository.findAllByStatusAndShowDateStartBeforeAndShowDateEndAfter(status, currentDate, currentDate, pageRequest);
    }
    public List<Movie> getMoviesShowingHome(Boolean status) {
        Date currentDate = new Date(); // Ngày hiện tại
        return movieRepository.findAllByStatusAndShowDateStartBeforeAndShowDateEndAfter(status, currentDate, currentDate);
    }
    public Page<Movie> getMoviesUpcoming(Boolean status, Integer page, Integer size) {
        Date currentDate = new Date(); // Ngày hiện tại
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("publishedAt").descending()); // page trong jpa bắt đầu từ 0
        return movieRepository.findByStatusAndShowDateStartBefore(status,currentDate,pageRequest);
    }
    public List<Movie> getMoviesUpcomingHome(Boolean status) {
        Date currentDate = new Date(); // Ngày hiện tại
        return movieRepository.findByStatusAndShowDateStartAfter(status,currentDate);
    }
    public List<Movie> getTop5MoviesUpcomingHome(Boolean status) {
        Date currentDate = new Date(); // Ngày hiện tại
        return movieRepository.findByStatusAndShowDateStartAfter(status,currentDate);
    }
    public List<Movie> searchMovie(String genre,Integer year, String country,String keyword){
        Specification<Movie> spec = MovieSpecifications.findMovies(genre, country, year, keyword, true);
        return movieRepository.findAll(spec);
    }

    public Movie getMovie(Integer id, String slug, boolean status) {
        return movieRepository.findByIdAndSlugAndStatus(id, slug, status).orElse(null);
    }
}
