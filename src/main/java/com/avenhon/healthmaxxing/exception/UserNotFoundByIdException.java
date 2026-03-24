package com.avenhon.healthmaxxing.exception;

public class UserNotFoundByIdException extends RuntimeException {
    public UserNotFoundByIdException(Long id) {
        super("User with id " + id + " not found");
    }
}
