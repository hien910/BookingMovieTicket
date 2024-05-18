package com.example.bookingmovieticket.rest;

import com.example.bookingmovieticket.entity.Review;
import com.example.bookingmovieticket.model.request.UpsertReviewRequest;
import com.example.bookingmovieticket.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewApi {
    private final ReviewService reviewService;

    // Tạo review - POST
    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody UpsertReviewRequest request) {
        Review review = reviewService.createReview(request);
        return new ResponseEntity<>(review, HttpStatus.CREATED); // 201
    }

    // Cập nhật review - PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Integer id, @RequestBody UpsertReviewRequest request) {
        Review review = reviewService.updateReview(id, request);
        return ResponseEntity.ok(review); // 200
    }

    // Xóa review - DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build(); // 204
    }
}
