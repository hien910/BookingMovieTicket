package com.example.bookingmovieticket.repository;

import com.example.bookingmovieticket.entity.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director,Integer> {

    Director findByName(String name);

    Director findByNameAndIdNot(String name, Integer id);
}
