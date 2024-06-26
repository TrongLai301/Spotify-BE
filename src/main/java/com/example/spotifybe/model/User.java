package com.example.spotifybe.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userID;
    @Column(nullable = false, unique = true)
    private String userName;
    private String phone;
    private String email;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    private String avatar;

    public Long getUserId() {
        return userID;
    }

    public User setUserId(Long id) {
        this.userID = id;
        return this;
    }
}
