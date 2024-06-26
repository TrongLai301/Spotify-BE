package com.example.spotifybe.repository;

import com.example.spotifybe.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRolesRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleName);
}
