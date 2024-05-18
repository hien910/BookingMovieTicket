package com.example.bookingmovieticket.repository;

import com.example.bookingmovieticket.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findReviewByMovie_IdOrderByCreatedAtDesc(Integer id);

    List<Review> findReviewByMovie_Id(Integer id);
}
