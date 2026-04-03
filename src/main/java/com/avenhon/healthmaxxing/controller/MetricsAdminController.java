package com.avenhon.healthmaxxing.controller;

import com.avenhon.healthmaxxing.dto.CreateMetricsAdminRequest;
import com.avenhon.healthmaxxing.dto.MetricsResponse;
import com.avenhon.healthmaxxing.entity.Metrics;
import com.avenhon.healthmaxxing.mappers.MetricsMapper;
import com.avenhon.healthmaxxing.service.MetricsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.avenhon.healthmaxxing.mappers.MetricsMapper.toResponse;

@RestController
@RequestMapping("/api/v1/admin/metrics")
public class MetricsAdminController {
    private final MetricsService metricsService;

    public MetricsAdminController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping
    public List<MetricsResponse> getAllMetrics() {
        return metricsService.getAllMetrics()
                .stream()
                .map(MetricsMapper::toResponse)
                .toList();
    }

    @GetMapping("/{metricsId}")
    public MetricsResponse getMetrics(@PathVariable Long metricsId) {
        Metrics metrics = metricsService.getMetricsById(metricsId);

        return toResponse(metrics);
    }

    @GetMapping("/{metricsId}/bmi")
    public float getMetricsBmi(@PathVariable Long metricsId) {
        Metrics metrics = metricsService.getMetricsById(metricsId);

        return metrics.getBmi();
    }

    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public MetricsResponse createMetrics(@RequestBody CreateMetricsAdminRequest metricsDTO) {
        Metrics metrics = metricsService.createMetrics(
                metricsDTO.sex(),
                metricsDTO.height(),
                metricsDTO.weight(),
                metricsDTO.steps(),
                metricsDTO.sleepTime(),
                metricsDTO.wakeTime(),
                metricsDTO.userId()
        );

        return toResponse(metrics);
    }
}
