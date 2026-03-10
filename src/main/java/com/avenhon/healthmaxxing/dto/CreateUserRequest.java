package com.avenhon.healthmaxxing.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
   @NotNull @Email
   String email,
   @NotNull
   String username,
   @NotNull
   String password
) {}
