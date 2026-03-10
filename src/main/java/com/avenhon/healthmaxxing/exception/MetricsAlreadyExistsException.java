package com.avenhon.healthmaxxing.exception;

public class MetricsAlreadyExistsException extends RuntimeException {
    public MetricsAlreadyExistsException() {
        super("Today metrics is already exists! Fill your metrics tomorrow!");
    }
}
