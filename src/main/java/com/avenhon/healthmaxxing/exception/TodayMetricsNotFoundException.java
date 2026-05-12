package com.avenhon.healthmaxxing.exception;

public class TodayMetricsNotFoundException extends RuntimeException {
    public TodayMetricsNotFoundException() {
        super("Metrics not found for today");
    }
}
