package com.example.bookingmovieticket.service;

import com.example.bookingmovieticket.entity.Movie;
import com.example.bookingmovieticket.entity.Review;
import com.example.bookingmovieticket.entity.User;
import com.example.bookingmovieticket.exception.BadRequestException;
import com.example.bookingmovieticket.exception.ResourceNotFoundException;
import com.example.bookingmovieticket.model.request.UpsertReviewRequest;
import com.example.bookingmovieticket.repository.MovieRepository;
import com.example.bookingmovieticket.repository.ReviewRepository;
import com.example.bookingmovieticket.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public List<Review> getReviewsByMovie(Integer id){
        return reviewRepository.findReviewByMovie_IdOrderByCreatedAtDesc(id);
    }
    // Tạo review
    public Review createReview(UpsertReviewRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userEmail = authentication.getName();

        // Tạo đối tượng User từ thông tin trích xuất được
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với email: " + userEmail));


        // Kiểm tra movieId có tồn tại không?
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy movie có id: " + request.getMovieId()));

        // Tạo review
        Review review = Review.builder()
                .comment(request.getComment())
                .rating(request.getRating())
                .user(user)
                .movie(movie)
                .build();

        return reviewRepository.save(review);
    }

    // Cập nhật review
    public Review updateReview(Integer id, UpsertReviewRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userEmail = authentication.getName();

        // Tạo đối tượng User từ thông tin trích xuất được
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với email: " + userEmail));

        // Kiểm tra movieId có tồn tại không?
        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy movie có id: " + request.getMovieId()));

        // Kiểm tra review có tồn tại không?
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy review có id: " + id));

        // Kiểm tra xem review có phải của user đang login không? Nếu không báo lỗi
        if (!review.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Bạn không có quyền cập nhật review này");
        }

        // Kiểm tra xem review có phải của movie không? Nếu không báo lỗi
        if (!review.getMovie().getId().equals(movie.getId())) {
            throw new BadRequestException("Review này không phải của movie này");
        }

        // Cập nhật review
        review.setComment(request.getComment());
        review.setRating(request.getRating());

        return reviewRepository.save(review);
    }

    // Xóa review
    public void deleteReview(Integer id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userEmail = authentication.getName();

        // Tạo đối tượng User từ thông tin trích xuất được
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng với email: " + userEmail));

        // Kiểm tra review có tồn tại không?
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy review có id: " + id));

        // Kiểm tra xem review có phải của user đang login không? Nếu không báo lỗi
        if (!review.getUser().getId().equals(user.getId())) {
            throw new BadRequestException("Bạn không có quyền cập nhật review này");
        }

        reviewRepository.delete(review);
    }
}
