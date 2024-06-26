package com.example.spotifybe.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {
    private long id;
    private String token;
    private String type = "Bearer";
    private String userName;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(String accessToken, Long id, String userName, Collection<? extends GrantedAuthority> roles) {
        this.token = accessToken;
        this.id = id;
        this.userName = userName;
        this.roles = roles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public Collection<? extends GrantedAuthority> getRoles() {
        return roles;
    }
}
