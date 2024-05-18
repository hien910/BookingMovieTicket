package com.example.bookingmovieticket.repository;

import com.example.bookingmovieticket.entity.User;
import com.example.bookingmovieticket.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findUserById(Integer id);

    User getUserById(Integer id);
    User getUserByEmail(String email);

    Optional<User> findByEmail(String email);

    List<User> findByRole(UserRole userRole);
//    List<User> findUsersOr();
}
