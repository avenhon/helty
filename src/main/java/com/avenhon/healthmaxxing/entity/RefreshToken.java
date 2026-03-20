package com.avenhon.healthmaxxing.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expirationDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public RefreshToken() {}

    public RefreshToken(String token, Instant expirationDate, User user) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public Instant getExpirationDate() {
        return expirationDate;
    }

    public User getUser() {
        return user;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpirationDate(Instant expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
