package com.avenhon.healthmaxxing.exception;

public class RefreshTokenExpiredException extends RuntimeException {
    public RefreshTokenExpiredException(String token) {
        super("Refresh token " + token + " is expired!");
    }
}
