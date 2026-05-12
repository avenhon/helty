package com.avenhon.healthmaxxing.service;

import com.avenhon.healthmaxxing.entity.Metrics;
import com.avenhon.healthmaxxing.enums.Sex;
import com.avenhon.healthmaxxing.exception.MetricsNotFoundException;
import com.avenhon.healthmaxxing.exception.TodayMetricsNotFoundException;
import com.avenhon.healthmaxxing.repository.MetricsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MetricsServiceTest {
    @Mock
    private MetricsRepository metricsRepository;

    @InjectMocks
    private MetricsService metricsService;

    private final Instant now = Instant.now();

    @Test
    public void metricsService_Handle_GetMetricsById() {
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

        when(metricsRepository.findById(1L)).thenReturn(Optional.of(metrics));

        Metrics foundMetrics = metricsService.getMetricsById(1L);

        assertNotNull(foundMetrics);
        assertEquals(68, foundMetrics.getWeight());
    }

    @Test
    public void metricsService_Handle_GetTodayMetrics() {
        Metrics metrics1 = Metrics.builder()
                .id(1L)
                .sex(Sex.MALE)
                .weight(68)
                .height(1.88F)
                .steps(2000)
                .sleepTime(now.minus(8, ChronoUnit.HOURS))
                .wakeTime(now)
                .localDate(LocalDate.now())
                .build();

        Metrics metrics2 = Metrics.builder()
                .id(2L)
                .sex(Sex.MALE)
                .weight(68)
                .height(1.88F)
                .steps(2000)
                .sleepTime(now.minus(8, ChronoUnit.HOURS))
                .wakeTime(now)
                .localDate(LocalDate.now())
                .build();

        when(metricsRepository.findAllByUserId(1L)).thenReturn(List.of(metrics1, metrics2));

        Metrics todayMetrics = metricsService.getTodayMetricsByUserId(1L);

        assertEquals(metrics2, todayMetrics);
    }

    @Test
    public void metricsService_Handle_MetricsByIdNotFound() {
        when(metricsRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(MetricsNotFoundException.class, () ->
                metricsService.getMetricsById(1L)
        );
    }

    @Test
    public void metricsService_Handle_TodayMetricsNotFound() {
        when(metricsRepository.findAllByUserId(1L)).thenReturn(List.of());

        assertThrows(TodayMetricsNotFoundException.class, () ->
                metricsService.getTodayMetricsByUserId(1L)
        );
    }

    @Test
    public void metricsService_Handle_TodayMetricsNotExists() {
        Metrics metrics1 = Metrics.builder()
                .id(1L)
                .sex(Sex.MALE)
                .weight(68)
                .height(1.88F)
                .steps(2000)
                .sleepTime(now.minus(8, ChronoUnit.HOURS))
                .wakeTime(now)
                .localDate(LocalDate.of(2026, 5, 11))
                .build();

        Metrics metrics2 = Metrics.builder()
                .id(2L)
                .sex(Sex.MALE)
                .weight(68)
                .height(1.88F)
                .steps(2000)
                .sleepTime(now.minus(8, ChronoUnit.HOURS))
                .wakeTime(now)
                .localDate(LocalDate.of(2026, 5, 12))
                .build();
        when(metricsRepository.findAllByUserId(1L)).thenReturn(List.of(metrics1, metrics2));

        assertThrows(TodayMetricsNotFoundException.class, () ->
                metricsService.getTodayMetricsByUserId(1L)
        );
    }
}
