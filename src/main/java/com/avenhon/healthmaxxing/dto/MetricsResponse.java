package com.avenhon.healthmaxxing.dto;

import com.avenhon.healthmaxxing.enums.Sex;

import java.time.LocalDate;

public record MetricsResponse(
        Long id,
        Sex sex,
        float height,
        float weight,
        float bmi,
        LocalDate localDate
) {}
