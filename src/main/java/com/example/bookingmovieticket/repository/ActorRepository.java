package com.example.bookingmovieticket.repository;

import com.example.bookingmovieticket.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
    Actor findByName(String name);

    Actor findByNameAndIdNot(String name, Integer id);
}
