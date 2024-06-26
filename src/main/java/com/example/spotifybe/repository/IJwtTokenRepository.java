package com.example.spotifybe.repository;

import com.example.spotifybe.model.JwtToken;
import com.example.spotifybe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IJwtTokenRepository extends JpaRepository<JwtToken, Long> {
    JwtToken findByTokenEquals(String token);
    JwtToken findByUser(User user);
}
