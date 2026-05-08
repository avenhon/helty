package com.avenhon.healthmaxxing.service;

import com.avenhon.healthmaxxing.entity.RefreshToken;
import com.avenhon.healthmaxxing.entity.User;
import com.avenhon.healthmaxxing.exception.RefreshTokenNotFoundException;
import com.avenhon.healthmaxxing.exception.UserNotFoundException;
import com.avenhon.healthmaxxing.repository.RefreshTokenRepository;
import com.avenhon.healthmaxxing.repository.UserRepository;
import com.avenhon.healthmaxxing.security.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenHashService tokenHashService;
    private final JwtUtil jwtUtils;
    private final UserService userService;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, TokenHashService tokenHashService, JwtUtil jwtUtils, UserService userService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenHashService = tokenHashService;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    public void createRefreshToken(String token, Long userId) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(tokenHashService.hash(token))
                .expirationDate(jwtUtils.extractExpiration(token).toInstant())
                .user(userService.getUserById(userId))
                .build();

        refreshTokenRepository.save(refreshToken);
    }

    public void updateRefreshToken(RefreshToken refreshToken, String newRefreshToken) {
        refreshToken.setToken(tokenHashService.hash(newRefreshToken));
        refreshToken.setExpirationDate(jwtUtils.extractExpiration(newRefreshToken).toInstant());

        refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByUserId(Long userId) {
        User user = userService.getUserById(userId);

        return refreshTokenRepository.findByUser(user).orElseThrow(() -> new RefreshTokenNotFoundException(user.getUsername()));
    }
}
