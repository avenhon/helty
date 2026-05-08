package com.avenhon.healthmaxxing.entity;

import com.avenhon.healthmaxxing.enums.Sex;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "metrics")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Metrics {
    private @Id
    @GeneratedValue Long id;

    private Sex sex;
    private float height;
    private float weight;
    private Integer steps;
    private Instant sleepTime;
    private Instant wakeTime;

    @Column(unique = true, nullable = false)
    private LocalDate localDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public float getBmi() {
        return weight / (height * height);
    }

    public float getSleepHours() {
        Duration duration = Duration.between(getSleepTime(), getWakeTime());

        return duration.toMinutes() / 60.0f;
    }
}
