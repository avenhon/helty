package com.avenhon.healthmaxxing.dto;


import com.avenhon.healthmaxxing.enums.Sex;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

public record CreateMetricsAdminRequest(
    @NotNull Sex sex,
    @Positive float height,
    @Positive float weight,
    @NotNull Integer steps,
    @NotNull Instant sleepTime,
    @NotNull Instant wakeTime,
    @NotNull Long userId
) {
    public CreateMetricsAdminRequest {
        if (height > 3) {
            height = height / 100f;
        }
    }
}