package com.avenhon.healthmaxxing.service;

import com.avenhon.healthmaxxing.dto.InsightsResponse;
import com.avenhon.healthmaxxing.entity.Metrics;
import com.avenhon.healthmaxxing.enums.Sex;
import com.avenhon.healthmaxxing.repository.MetricsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InsightsServiceTest {
    @Mock
    private MetricsService metricsService;

    @InjectMocks
    private InsightsService insightsService;

    @Test
    public void InsightsService_Handle_InsightsCreation() {
        Instant now = Instant.now();

        Metrics metrics = Metrics.builder()
                .id(1L)
                .sex(Sex.MALE)
                .weight(68)
                .height(188)
                .steps(2000)
                .sleepTime(now.minus(8, ChronoUnit.HOURS))
                .wakeTime(now)
                .build();
        when(metricsService.getTodayMetricsByUserId(1L)).thenReturn(metrics);

        InsightsResponse insights = insightsService.prepareTodayInsight(1L);

        assertNotNull(insights);
    }
}
