package com.avenhon.healthmaxxing.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum InsightMetricsStatuses {
    EXCELLENT("excellent"),
    GOOD("good"),
    AVERAGE("average"),
    POOR("poor"),
    CRITICAL("critical");

    private final String value;

    InsightMetricsStatuses(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
