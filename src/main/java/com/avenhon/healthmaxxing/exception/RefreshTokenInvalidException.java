package com.avenhon.healthmaxxing.exception;

public class RefreshTokenInvalidException extends RuntimeException {
    public RefreshTokenInvalidException(String token) {
        super("Invalid " + token + " refresh token");
    }
}
