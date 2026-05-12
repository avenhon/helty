package com.avenhon.healthmaxxing.service;

import com.avenhon.healthmaxxing.dto.InsightsResponse;
import com.avenhon.healthmaxxing.entity.Metrics;
import com.avenhon.healthmaxxing.enums.InsightMetricsStatuses;
import com.avenhon.healthmaxxing.enums.Sex;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InsightsServiceTest {
    @Mock
    private MetricsService metricsService;

    @InjectMocks
    private InsightsService insightsService;

    @Test
    public void insightsService_Handle_InsightsCreation() {
        Instant now = Instant.now();

        Metrics metrics = Metrics.builder()
                .id(1L)
                .sex(Sex.MALE)
                .weight(68)
                .height(1.88F)
                .steps(2000)
                .sleepTime(now.minus(8, ChronoUnit.HOURS))
                .wakeTime(now)
                .localDate(LocalDate.now())
                .build();
        when(metricsService.getTodayMetricsByUserId(1L)).thenReturn(metrics);

        InsightsResponse insights = insightsService.prepareTodayInsight(1L);

        int sleepScore = (int) Math.max(0, Math.min(100, Math.round(100 - 8 * Math.pow(((8 * 60.0 / 90.0) - 5), 2))));
        int bmiScore = 100;
        int stepsScore = (int) Math.max(0, Math.min(100, Math.round(100 * 2000.0 / 7000)));
        int expectedScore = (int) (sleepScore * 0.5 + bmiScore * 0.2 + stepsScore * 0.3);

        assertEquals(expectedScore, insights.score());
    }

    @Test
    public void insightsService_Handle_PerfectInsightsCreation() {
        Instant now = Instant.now();

        Metrics metrics = Metrics.builder()
                .id(1L)
                .sex(Sex.MALE)
                .weight(68)
                .height(1.88F)
                .steps(7000)
                .sleepTime(now.minus(450, ChronoUnit.MINUTES))
                .wakeTime(now)
                .localDate(LocalDate.now())
                .build();
        when(metricsService.getTodayMetricsByUserId(1L)).thenReturn(metrics);

        InsightsResponse insights = insightsService.prepareTodayInsight(1L);

        assertEquals(100, insights.score());
    }

    @Test
    public void insightsService_Handle_CriticalInsightsCreation() {
        Instant now = Instant.now();

        Metrics metrics = Metrics.builder()
                .id(1L)
                .sex(Sex.MALE)
                .weight(150)
                .height(1.88F)
                .steps(1500)
                .sleepTime(now.minus(2, ChronoUnit.HOURS))
                .wakeTime(now)
                .localDate(LocalDate.now())
                .build();
        when(metricsService.getTodayMetricsByUserId(1L)).thenReturn(metrics);

        InsightsResponse insights = insightsService.prepareTodayInsight(1L);

        assertEquals(InsightMetricsStatuses.CRITICAL, insights.status());
        assertTrue(insights.summary().contains("resting and sleep more"));
        assertTrue(insights.summary().contains("diet"));
        assertTrue(insights.summary().contains("daily steps count"));
    }
}
