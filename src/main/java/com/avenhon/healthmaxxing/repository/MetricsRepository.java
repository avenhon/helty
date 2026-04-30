package com.avenhon.healthmaxxing.repository;

import com.avenhon.healthmaxxing.entity.Metrics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MetricsRepository extends JpaRepository<Metrics, Long> {
    List<Metrics> findAllByUserId(Long userId);
}
