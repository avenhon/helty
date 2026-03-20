package com.avenhon.healthmaxxing.exception;

public class RefreshTokenNotFoundException extends RuntimeException {
    public RefreshTokenNotFoundException(String token) {
        super("Token " + token + " not found!");
    }
}
