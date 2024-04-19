package com.example.bookingmovieticket.service;

import com.example.bookingmovieticket.entity.Blog;
import com.example.bookingmovieticket.repository.BlogRepository;
import com.github.slugify.Slugify;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;
    private final HttpSession session;

    Slugify slugify = Slugify.builder().build();

    public Blog findBlogByIdAndSlug(Integer id, String slug, boolean status){
        return blogRepository.findBlogByIdAndSlugAndStatus(id,slug, status);
    }
    public Page<Blog> getBlogByStatus(Boolean status, Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("publishedAt").descending()); // page trong jpa bắt đầu từ 0
        return blogRepository.findBlogByStatusOrderByPublishedAtDesc( status, pageRequest);
    }
    public List<Blog> findAll() {
        return blogRepository.findAll(Sort.by("createdAt").descending());
    }

    public Page<Blog> findBlogByStatusOrderByPublishedAtDesc(boolean status, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by("publishedAt").descending());
        return blogRepository.findBlogByStatusOrderByPublishedAtDesc(status ,pageRequest);
    }
}
