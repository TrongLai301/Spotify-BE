package com.example.spotifybe.service.impl;

import com.example.spotifybe.model.Role;
import com.example.spotifybe.repository.IRolesRepository;
import com.example.spotifybe.service.IRolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolesService implements IRolesService {
    @Autowired
    private IRolesRepository rolesRepository;

    @Override
    public Iterable<Role> findAll() {
        return rolesRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return rolesRepository.findById(id);
    }

    @Override
    public void save(Role role) {
        rolesRepository.save(role);
    }

    @Override
    public Role findByName(String name) {
        return rolesRepository.findByName(name);
    }
}
