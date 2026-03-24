package com.avenhon.healthmaxxing.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String findInput) {
        super("User " + findInput + " not found!");
    }
}
