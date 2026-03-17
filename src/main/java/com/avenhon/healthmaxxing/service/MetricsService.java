package com.avenhon.healthmaxxing.service;

import com.avenhon.healthmaxxing.entity.Metrics;
import com.avenhon.healthmaxxing.entity.User;
import com.avenhon.healthmaxxing.enums.Sex;
import com.avenhon.healthmaxxing.exception.MetricsAlreadyExistsException;
import com.avenhon.healthmaxxing.exception.MetricsNotFoundException;
import com.avenhon.healthmaxxing.exception.UserNotFoundException;
import com.avenhon.healthmaxxing.repository.MetricsRepository;
import com.avenhon.healthmaxxing.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MetricsService {
    private final MetricsRepository metricsRepository;
    private final UserRepository userRepository;

    public MetricsService(MetricsRepository metricsRepository, UserRepository userRepository) {
        this.metricsRepository = metricsRepository;
        this.userRepository = userRepository;
    }

    public List<Metrics> getAllMetrics() {
        return metricsRepository.findAll();
    }

    public Metrics getMetricsById(Long metricsId) {
        return metricsRepository.findById(metricsId).orElseThrow(() -> new MetricsNotFoundException(metricsId));
    }

    public Metrics createMetrics(Sex sex, float height, float weight, Integer steps, Long userId) {
        // Manual steps input it's a part of MVP, later will be Google Fit/Apple Health integration
        Metrics newMetrics = new Metrics(sex, height, weight, steps);

        LocalDate localDate = LocalDate.now();

        newMetrics.setLocalDate(localDate);

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        user.addMetrics(newMetrics);

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new MetricsAlreadyExistsException();
        }
        return newMetrics;
    }
}
