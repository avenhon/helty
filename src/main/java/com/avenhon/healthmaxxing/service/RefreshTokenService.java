package com.avenhon.healthmaxxing.service;

import com.avenhon.healthmaxxing.entity.RefreshToken;
import com.avenhon.healthmaxxing.entity.User;
import com.avenhon.healthmaxxing.exception.RefreshTokenNotFoundException;
import com.avenhon.healthmaxxing.repository.RefreshTokenRepository;
import com.avenhon.healthmaxxing.repository.UserRepository;
import com.avenhon.healthmaxxing.security.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final TokenHashService tokenHashService;
    private final JwtUtil jwtUtils;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, TokenHashService tokenHashService, JwtUtil jwtUtils) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.tokenHashService = tokenHashService;
        this.jwtUtils = jwtUtils;
    }

    public void createRefreshToken(String token, String username) {
        RefreshToken refreshToken = new RefreshToken(tokenHashService.hash(token), jwtUtils.extractExpiration(token).toInstant(), userRepository.findByUsername(username));

        refreshTokenRepository.save(refreshToken);
    }

    public void updateRefreshToken(RefreshToken refreshToken, String newRefreshToken) {
        refreshToken.setToken(tokenHashService.hash(newRefreshToken));
        refreshToken.setExpirationDate(jwtUtils.extractExpiration(newRefreshToken).toInstant());

        refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByUser(String username) {
        User user = userRepository.findByUsername(username);

        return refreshTokenRepository.findByUser(user).orElseThrow(() -> new RefreshTokenNotFoundException(username));
    }
}
