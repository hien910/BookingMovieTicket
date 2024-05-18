package com.example.bookingmovieticket.repository;

import com.example.bookingmovieticket.entity.TokenConfirm;
import com.example.bookingmovieticket.model.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenConfirmRepository extends JpaRepository<TokenConfirm, Integer> {
    Optional<TokenConfirm> findByToken(String token);

    Optional<TokenConfirm> findByTokenAndTokenType(String token, TokenType tokenType);

}
