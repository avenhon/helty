package com.avenhon.healthmaxxing.dto;

public record AuthResponse(
        String accessToken,
        String refreshToken
)
{}
