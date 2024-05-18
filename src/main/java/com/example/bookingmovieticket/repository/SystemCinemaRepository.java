package com.example.bookingmovieticket.repository;

import com.example.bookingmovieticket.entity.SystemCinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemCinemaRepository extends JpaRepository<SystemCinema, Integer> {
}
