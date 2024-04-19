package com.example.bookingmovieticket.service;

import com.example.bookingmovieticket.entity.Genre;
import com.example.bookingmovieticket.exception.ResourceNotFoundException;
import com.example.bookingmovieticket.model.request.UpsertGenreRequest;
import com.example.bookingmovieticket.repository.GenreRepository;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;

    Slugify slugify = Slugify.builder().build();
    public Genre creatGenre(UpsertGenreRequest request) {
        Genre genre = Genre.builder()
                .name(request.getName())
                .slug(slugify.slugify(request.getName()))
                .createdAt(new Date())
                .updatedAt(new Date())
                .build();
        return genreRepository.save(genre);
    }

    public Genre updateGenre(UpsertGenreRequest request,Integer id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thể loại với id = " + id));
        genre.setName(request.getName());
        genre.setUpdatedAt(new Date());
        genre.setCreatedAt(new Date());
        genre.setSlug(slugify.slugify(request.getName()));

        return genreRepository.save(genre);
    }

    public void deleteGenre(Integer id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thể loại với id = " + id));
        genreRepository.delete(genre);
    }

    public List<Genre> getAll() {
        return genreRepository.findAll();
    }
}
