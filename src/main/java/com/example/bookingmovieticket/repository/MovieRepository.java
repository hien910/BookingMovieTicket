package com.example.bookingmovieticket.repository;

import com.example.bookingmovieticket.entity.Genre;
import com.example.bookingmovieticket.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;


// Movie : Tên Entity
// Integer: Kiểu dữ liệu của khóa chính
@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Page<Movie> findByStatus(Boolean status, PageRequest pageRequest);
    List<Movie> findAllByStatus(Boolean status);
    Page<Movie> findAllByStatusAndShowDateStartBeforeAndShowDateEndAfter(Boolean status, Date dateStart, Date dateEnd, PageRequest pageRequest);
    List<Movie> findAllByStatusAndShowDateStartBeforeAndShowDateEndAfter(Boolean status, Date dateStart, Date dateEnd  );
//    Page<Movie> getMoviesShowing(Boolean status, PageRequest pageRequest){
//        Date currentDate = new Date(); // Ngày hiện tại
//
//        return movieList.stream()
//                .filter(movie -> movie.getStatus() && currentDate.after(movie.getShowDateStart()) && currentDate.before(movie.getShowDateEnd()))
//                .collect(Collectors.toList());
//    }
    Page<Movie> findByStatusAndShowDateStartBefore(Boolean status, Date currentDate, PageRequest pageRequest);
    List<Movie> findByStatusAndShowDateStartAfter(Boolean status, Date currentDate);

    List<Movie> findMoviesByStatusAndGenres_NameIgnoreCaseAndReleaseYearAndCountry_NameAndTitleContainingIgnoreCase(
            Boolean status,
            String genreName,
            Integer year,
            String countryName,
            String keyword);

    Optional<Movie> findByIdAndSlugAndStatus(Integer id, String slug, boolean status);
    List<Movie> findAll(Specification<Movie> specification);
//    List<Movie> findTop5ByRatingAndStatus(Boolean status);


}