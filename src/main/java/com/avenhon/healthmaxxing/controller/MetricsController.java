package com.avenhon.healthmaxxing.controller;

import com.avenhon.healthmaxxing.dto.CreateMetricsUserRequest;
import com.avenhon.healthmaxxing.dto.MetricsResponse;
import com.avenhon.healthmaxxing.entity.Metrics;
import com.avenhon.healthmaxxing.entity.User;
import com.avenhon.healthmaxxing.mappers.MetricsMapper;
import com.avenhon.healthmaxxing.service.MetricsService;
import com.avenhon.healthmaxxing.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static com.avenhon.healthmaxxing.mappers.MetricsMapper.toResponse;

@RestController
@RequestMapping("/api/v1/metrics")
public class MetricsController {
    private final MetricsService metricsService;
    private final UserService userService;

    public MetricsController(MetricsService metricsService, UserService userService) {
        this.metricsService = metricsService;
        this.userService = userService;
    }

    @GetMapping
    public List<MetricsResponse> getAllMetrics(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());

        return user.getMetrics()
                .stream()
                .map(MetricsMapper::toResponse)
                .toList();
    }

    @GetMapping("/today")
    public MetricsResponse getTodayMetrics(Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());

        List<Metrics> metricsList = new ArrayList<>(user.getMetrics());

        if (metricsList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Metrics not found for today");
        }

        Metrics latestMetrics = metricsList.getLast();

        return toResponse(latestMetrics);
    }

    @GetMapping("/today/bmi")
    public float getTodayMetricsBmi(Authentication authentication) {
        Metrics latestMetrics = getTodayMetrics(authentication.getName());

        return latestMetrics.getBmi();
    }

    @GetMapping("/today/sleep")
    public float getTodaySleep(Authentication authentication) {
        Metrics latestMetrics = getTodayMetrics(authentication.getName());

        return latestMetrics.getSleepHours();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MetricsResponse createUserMetrics(@RequestBody CreateMetricsUserRequest metricsDTO, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName());

        Metrics metrics = metricsService.createMetrics(
                metricsDTO.sex(),
                metricsDTO.height(),
                metricsDTO.weight(),
                metricsDTO.steps(),
                metricsDTO.sleepTime(),
                metricsDTO.wakeTime(),
                user.getId()
        );

        return toResponse(metrics);
    }

    // Helpers
    private Metrics getTodayMetrics(String username) {
        User user = userService.getUserByUsername(username);

        List<Metrics> metricsList = new ArrayList<>(user.getMetrics());

        if (metricsList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Metrics not found for today");
        }

        return metricsList.getLast();
    }
}
