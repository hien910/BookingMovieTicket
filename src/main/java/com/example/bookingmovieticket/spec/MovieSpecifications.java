package com.example.bookingmovieticket.spec;

import com.example.bookingmovieticket.entity.Movie;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class MovieSpecifications {

    public static Specification<Movie> findMovies(String genreName, String countryName, Integer releaseYear, String titleKeyword, Boolean status) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (genreName != null && !genreName.isEmpty()) {
                predicates.add(cb.equal(root.join("genres").get("name"), genreName));
            }

            if (countryName != null && !countryName.isEmpty()) {
                predicates.add(cb.equal(root.get("country").get("name"), countryName));
            }

            if (releaseYear != null) {
                predicates.add(cb.equal(root.get("releaseYear"), releaseYear));
            }

            if (titleKeyword != null && !titleKeyword.isEmpty()) {
                predicates.add(cb.like(root.get("title"), "%" + titleKeyword + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
