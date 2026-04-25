package com.avenhon.healthmaxxing.dto;

import com.avenhon.healthmaxxing.enums.InsightMetricsStatuses;
import com.avenhon.healthmaxxing.enums.InsightMetricsTypes;

public record MetricInsightDTO(
        InsightMetricsTypes type,
        int score,
        InsightMetricsStatuses status
) {}
