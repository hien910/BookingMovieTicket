package com.example.bookingmovieticket.service;

import com.example.bookingmovieticket.entity.User;
import com.example.bookingmovieticket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    public List<User> findUserOfMonth(Date start, Date end){
//        return userRepository.findUserByCreatedAtBetweenOrderByCreatedAtDesc(start, end);
//    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
