package com.avenhon.healthmaxxing.dto;

import java.util.Set;

public record UserResponse(
  Long id,
  String email,
  String username,
  Set<MetricsResponse> metrics
) {}
