package com.avenhon.healthmaxxing.controller;

import com.avenhon.healthmaxxing.dto.CreateMetricsUserRequest;
import com.avenhon.healthmaxxing.dto.MetricsResponse;
import com.avenhon.healthmaxxing.entity.Metrics;
import com.avenhon.healthmaxxing.entity.User;
import com.avenhon.healthmaxxing.mappers.MetricsMapper;
import com.avenhon.healthmaxxing.security.CustomUserDetails;
import com.avenhon.healthmaxxing.service.MetricsService;
import com.avenhon.healthmaxxing.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
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
    public List<MetricsResponse> getAllMetrics(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Long userId = userDetails.getId();

        return metricsService.getMetricsByUserId(userId)
                .stream()
                .map(MetricsMapper::toResponse)
                .toList();
    }

    @GetMapping("/today")
    public MetricsResponse getTodayMetrics(@AuthenticationPrincipal CustomUserDetails userDetails) {
        Metrics today = metricsService.getTodayMetricsByUserId(userDetails.getId());
        return toResponse(today);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MetricsResponse createUserMetrics(@RequestBody CreateMetricsUserRequest metricsDTO,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userService.getUserById(userDetails.getId());

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
}
