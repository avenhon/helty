package com.avenhon.healthmaxxing.dto;

import com.avenhon.healthmaxxing.enums.Sex;
import com.avenhon.healthmaxxing.exception.SleepTimeInvalidException;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Duration;
import java.time.Instant;

public record CreateMetricsUserRequest(
        @NotNull Sex sex,
        @Positive float height,
        @Positive float weight,
        @NotNull Integer steps,
        @NotNull Instant sleepTime,
        @NotNull Instant wakeTime) {
    public CreateMetricsUserRequest {
        if (height > 3) {
            height = height / 100f;
        }

        Instant now = Instant.now();
        Duration duration = Duration.between(sleepTime, wakeTime);

        if (sleepTime.isAfter(wakeTime)) {
            throw new SleepTimeInvalidException();
        }

        if (wakeTime.isAfter(now) || sleepTime.isAfter(now)) {
            throw new SleepTimeInvalidException();
        }

        if (duration.toHours() > 24) {
            throw new SleepTimeInvalidException();
        }

        if (wakeTime.isBefore(now.minus(Duration.ofDays(2)))) {
            throw new SleepTimeInvalidException();
        }
    }
}
