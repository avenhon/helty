package com.avenhon.healthmaxxing.entity;

import com.avenhon.healthmaxxing.enums.Sex;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Metrics {
    private @Id
    @GeneratedValue Long id;

    private Sex sex;
    private float height;
    private float weight;

    @Column(unique = true, nullable = false)
    private LocalDate localDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    public Metrics() {}

    public Metrics(Sex sex, float height, float weight) {
        this.sex = sex;
        this.height = height;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public Sex getSex() {
        return sex;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public float getBmi() {
        return weight / (height * height);
    }

    public LocalDate getLocalDate() { return localDate; }

    public User getUser() {
        return user;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setLocalDate(LocalDate localDate) { this.localDate = localDate; }

    public void setUser(User user) {
        this.user = user;
    }
}
