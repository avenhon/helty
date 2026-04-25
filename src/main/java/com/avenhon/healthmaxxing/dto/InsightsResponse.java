package com.avenhon.healthmaxxing.dto;

import com.avenhon.healthmaxxing.enums.InsightMetricsStatuses;

import java.util.List;

public record InsightsResponse(
        Integer score,
        InsightMetricsStatuses status,
        List<MetricInsightDTO> metrics,
        String summary
) {}
