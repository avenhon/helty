package com.avenhon.healthmaxxing.dto;


import com.avenhon.healthmaxxing.enums.Sex;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateMetricsRequest(
    @NotNull Sex sex,
    @Positive float height,
    @Positive float weight,
    @NotNull Long userId
) {
    public CreateMetricsRequest {
        if (height > 3) {
            height = height / 100f;
        }
    }
}