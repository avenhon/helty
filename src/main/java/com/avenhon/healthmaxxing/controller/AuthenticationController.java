package com.avenhon.healthmaxxing.controller;

import com.avenhon.healthmaxxing.dto.AuthResponse;
import com.avenhon.healthmaxxing.dto.CreateUserRequest;
import com.avenhon.healthmaxxing.dto.RefreshTokenRequest;
import com.avenhon.healthmaxxing.entity.RefreshToken;
import com.avenhon.healthmaxxing.entity.User;
import com.avenhon.healthmaxxing.exception.RefreshTokenExpiredException;
import com.avenhon.healthmaxxing.exception.RefreshTokenInvalidException;
import com.avenhon.healthmaxxing.security.JwtUtil;
import com.avenhon.healthmaxxing.service.RefreshTokenService;
import com.avenhon.healthmaxxing.service.TokenHashService;
import com.avenhon.healthmaxxing.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenHashService tokenHashService;
    private final JwtUtil jwtUtils;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationController(
            AuthenticationManager authenticationManager,
            UserService userService,
            TokenHashService tokenHashService,
            JwtUtil jwtUtils,
            RefreshTokenService refreshTokenService
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenHashService = tokenHashService;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/signin")
    public AuthResponse authenticateUser(@RequestBody CreateUserRequest user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.username(),
                        user.password()
                )
        );

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        assert userDetails != null;
        String accessToken = jwtUtils.generateAccessToken(userDetails.getUsername());
        String refreshToken = jwtUtils.generateRefreshToken(userDetails.getUsername());

        refreshTokenService.createRefreshToken(refreshToken, userDetails.getUsername());

        return new AuthResponse(
                accessToken,
                refreshToken
        );
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@RequestBody User user) {
        return userService.createUser(user.getEmail(), user.getUsername(), user.getPassword());
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String rawRefreshToken = refreshTokenRequest.token();

        if (!jwtUtils.validateJwtToken(rawRefreshToken)) {
            throw new RefreshTokenInvalidException(rawRefreshToken);
        }

        String tokenUsername = jwtUtils.getUserFromToken(rawRefreshToken);

        RefreshToken refreshToken = refreshTokenService.findByUser(tokenUsername);

        if (!tokenHashService.hash(rawRefreshToken).equals(refreshToken.getToken())) {
            throw new RefreshTokenInvalidException(rawRefreshToken);
        }

        if (jwtUtils.isTokenExpired(rawRefreshToken)) {
            throw new RefreshTokenExpiredException(rawRefreshToken);
        }

        String newAccessToken = jwtUtils.generateAccessToken(tokenUsername);
        String newRefreshToken = jwtUtils.generateRefreshToken(tokenUsername);

        refreshTokenService.updateRefreshToken(refreshToken, newRefreshToken);

        return new AuthResponse(
                newAccessToken,
                newRefreshToken
        );
    }
}
