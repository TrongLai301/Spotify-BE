package com.example.spotifybe.service;

import com.example.spotifybe.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface IUserService extends UserDetailsService {


    User save(User user);
    Optional<User> findById(Long id);


    User findByEmail(String email);

    boolean checkLogin(User user);

    List<User> findAll();
}
