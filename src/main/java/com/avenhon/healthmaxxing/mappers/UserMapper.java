package com.avenhon.healthmaxxing.mappers;

import com.avenhon.healthmaxxing.dto.MetricsResponse;
import com.avenhon.healthmaxxing.dto.UserResponse;
import com.avenhon.healthmaxxing.entity.User;

import java.util.HashSet;
import java.util.Set;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        MetricsResponse metricsResponse = null;
        Set<MetricsResponse> metricsResponses = new HashSet<>();

        if (user.getMetrics() != null) {
            user.getMetrics().forEach(m -> metricsResponses.add(new MetricsResponse(
                    m.getId(),
                    m.getSex(),
                    m.getHeight(),
                    m.getWeight(),
                    m.getBmi(),
                    m.getSteps(),
                    m.getSleepTime(),
                    m.getWakeTime(),
                    m.getSleepHours(),
                    m.getLocalDate()
            )));
        }

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                metricsResponses
        );
    }
}
