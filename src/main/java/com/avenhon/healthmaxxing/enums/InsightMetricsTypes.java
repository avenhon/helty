package com.avenhon.healthmaxxing.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum InsightMetricsTypes {
    SLEEP("sleep"),
    BMI("bmi"),
    STEPS("steps");

    private final String value;

    InsightMetricsTypes(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
