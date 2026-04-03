package com.avenhon.healthmaxxing.dto;

import com.avenhon.healthmaxxing.enums.Sex;

import java.time.Instant;
import java.time.LocalDate;

public record MetricsResponse(
        Long id,
        Sex sex,
        float height,
        float weight,
        float bmi,
        Integer steps,
        Instant sleepTime,
        Instant wakeTime,
        LocalDate localDate
) {}
