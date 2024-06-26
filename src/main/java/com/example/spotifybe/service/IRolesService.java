package com.example.spotifybe.service;

import com.example.spotifybe.model.Role;

import java.util.Optional;

public interface IRolesService {
    Iterable<Role> findAll();

    Optional<Role> findById(Long id);

    void save(Role role);

    Role findByName(String name);
}
