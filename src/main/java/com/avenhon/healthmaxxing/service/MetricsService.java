package com.avenhon.healthmaxxing.service;

import com.avenhon.healthmaxxing.entity.Metrics;
import com.avenhon.healthmaxxing.entity.User;
import com.avenhon.healthmaxxing.enums.Sex;
import com.avenhon.healthmaxxing.exception.MetricsNotFoundException;
import com.avenhon.healthmaxxing.exception.TodayMetricsNotFoundException;
import com.avenhon.healthmaxxing.repository.MetricsRepository;
import com.avenhon.healthmaxxing.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MetricsService {
    private final MetricsRepository metricsRepository;
    private final UserService userService;

    public MetricsService(MetricsRepository metricsRepository, UserRepository userRepository, UserService userService) {
        this.metricsRepository = metricsRepository;
        this.userService = userService;
    }

    public List<Metrics> getAllMetrics() {
        return metricsRepository.findAll();
    }

    public List<Metrics> getMetricsByUserId(Long userId) {
        return metricsRepository.findAllByUserId(userId);
    }

    public Metrics getMetricsById(Long metricsId) {
        return metricsRepository.findById(metricsId).orElseThrow(() -> new MetricsNotFoundException(metricsId));
    }

    public Metrics getTodayMetricsByUserId(Long userId) {
        List<Metrics> metricsList = new ArrayList<>(metricsRepository.findAllByUserId(userId));

        if (metricsList.isEmpty()) {
            throw new TodayMetricsNotFoundException();
        }

        Metrics lastUserMetrics = metricsList.getLast();

        if (!lastUserMetrics.getLocalDate().equals(LocalDate.now())) {
            throw new TodayMetricsNotFoundException();
        }

        return lastUserMetrics;
    }

    public Metrics createMetrics(Sex sex, float height, float weight, Integer steps, Instant sleepTime, Instant wakeTime, Long userId) {
        // Manual steps input it's a part of MVP, later will be Google Fit/Apple Health integration
        Metrics newMetrics = Metrics.builder()
                .sex(sex)
                .height(height)
                .weight(weight)
                .steps(steps)
                .sleepTime(sleepTime)
                .wakeTime(wakeTime)
                .build();

        LocalDate localDate = LocalDate.now();

        newMetrics.setLocalDate(localDate);

        userService.addMetrics(userId, newMetrics);

        return newMetrics;
    }
}
