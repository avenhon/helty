package com.avenhon.healthmaxxing.controller;

import com.avenhon.healthmaxxing.dto.CreateMetricsRequest;
import com.avenhon.healthmaxxing.dto.MetricsResponse;
import com.avenhon.healthmaxxing.entity.Metrics;
import com.avenhon.healthmaxxing.mappers.MetricsMapper;
import com.avenhon.healthmaxxing.service.MetricsService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.avenhon.healthmaxxing.mappers.MetricsMapper.toResponse;

@RestController
@RequestMapping("/api/v1/metrics")
public class MetricsController {
    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MetricsResponse createMetrics(@RequestBody CreateMetricsRequest metricsDTO) {
        Metrics metrics = metricsService.createMetrics(
                metricsDTO.sex(),
                metricsDTO.height(),
                metricsDTO.weight(),
                metricsDTO.steps(),
                metricsDTO.userId()
        );

        return toResponse(metrics);
    }
}
