package com.avenhon.healthmaxxing.exception;

public class SleepTimeInvalidException extends RuntimeException {
    public SleepTimeInvalidException() {
        super("Sleep time is invalid!");
    }
}
