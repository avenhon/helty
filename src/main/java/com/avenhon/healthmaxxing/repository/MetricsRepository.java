package com.avenhon.healthmaxxing.repository;

import com.avenhon.healthmaxxing.entity.Metrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetricsRepository extends JpaRepository<Metrics, Long> {
}
