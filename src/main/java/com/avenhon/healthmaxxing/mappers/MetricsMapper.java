package com.avenhon.healthmaxxing.mappers;

import com.avenhon.healthmaxxing.dto.MetricsResponse;
import com.avenhon.healthmaxxing.entity.Metrics;

public class MetricsMapper {
    public static MetricsResponse toResponse(Metrics metrics) {
      return new MetricsResponse(
              metrics.getId(),
              metrics.getSex(),
              metrics.getHeight(),
              metrics.getWeight(),
              metrics.getBmi(),
              metrics.getSteps(),
              metrics.getSleepTime(),
              metrics.getWakeTime(),
              metrics.getLocalDate()
      );
    }
}
