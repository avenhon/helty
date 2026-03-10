package com.avenhon.healthmaxxing.exception;

public class MetricsNotFoundException extends RuntimeException {
    public MetricsNotFoundException(Long id) {
        super("Metrics with id " + id + " not found");
    }
}
