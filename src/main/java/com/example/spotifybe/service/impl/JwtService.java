package com.example.spotifybe.service.impl;

import com.example.spotifybe.model.CustomUserDetails;
import com.example.spotifybe.model.JwtToken;
import com.example.spotifybe.model.User;
import com.example.spotifybe.repository.IJwtTokenRepository;
import com.example.spotifybe.service.IUserService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Autowired
    private IJwtTokenRepository tokenRepository;
    @Autowired
    private IUserService userService;
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    private static final long EXPIRE_TIME = 86400000000L;
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class.getName());

    public String generateTokenLogin(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        User user =  userService.findById(((CustomUserDetails) authentication.getPrincipal()).getId()).get();
        JwtToken jwtToken =tokenRepository.findByUser(user);

        if (jwtToken != null) {
            tokenRepository.save(new JwtToken(jwtToken.getId(), user, Jwts.builder()
                    .setSubject((customUserDetails.getUsername()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + EXPIRE_TIME * 1000))
                    .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                    .compact(), true));
            return tokenRepository.findByUser(user).getToken();
        }else {
            tokenRepository.save(new JwtToken(user, Jwts.builder()
                    .setSubject((customUserDetails.getUsername()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + EXPIRE_TIME * 1000))
                    .signWith(SECRET_KEY, SignatureAlgorithm.HS512)
                    .compact(), true));
            return tokenRepository.findByUser(user).getToken();
        }
    }
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }

        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
