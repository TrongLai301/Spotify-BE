package com.example.spotifybe.controller;

import com.example.spotifybe.model.JwtResponse;
import com.example.spotifybe.model.Response;
import com.example.spotifybe.model.User;
import com.example.spotifybe.repository.IJwtTokenRepository;
import com.example.spotifybe.service.IUserService;
import com.example.spotifybe.service.impl.JwtService;
import com.example.spotifybe.service.impl.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
public class Controller {
    @Autowired
    private IJwtTokenRepository tokenRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private IUserService userService;
    @Autowired
    private RolesService rolesService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        List<User> users = userService.findAll();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userRes = userService.save(user);
        return new ResponseEntity<>(userRes, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody User user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            User currentUser = userService.findByEmail(user.getEmail());
            return ResponseEntity.ok(new Response("200", "Login complete",
                    new JwtResponse(jwt, currentUser.getUserId(), userDetails.getUsername(), userDetails.getAuthorities())));
        } catch (Exception e) {
            return ResponseEntity.ok(new Response("401", "Account or password is incorrect", null));
        }
    }
}
