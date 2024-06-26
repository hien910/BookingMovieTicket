package com.example.bookingmovieticket.repository;

import com.example.bookingmovieticket.entity.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
    List<Blog> findBlogByStatusOrderByPublishedAtDesc(Boolean status);

    Page<Blog> findBlogByStatusOrderByPublishedAtDesc(Boolean status, Pageable pageable);

    Blog findBlogByIdAndSlugAndStatus(Integer id, String slug, Boolean status);

    List<Blog> findByUser_idOrderByCreatedAtDesc(Integer id);

    List<Blog> findBlogByCreatedAtBetweenOrderByCreatedAtDesc(Date createdAt, Date createdAt2);
}
