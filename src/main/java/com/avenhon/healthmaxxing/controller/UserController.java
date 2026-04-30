package com.avenhon.healthmaxxing.controller;

import static com.avenhon.healthmaxxing.mappers.UserMapper.toResponse;

import com.avenhon.healthmaxxing.dto.UserResponse;
import com.avenhon.healthmaxxing.security.CustomUserDetails;
import com.avenhon.healthmaxxing.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse getUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return toResponse(userService.getUserById(userDetails.getId()));
    }
}
