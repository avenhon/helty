package com.avenhon.healthmaxxing.controller;

import com.avenhon.healthmaxxing.dto.UserResponse;
import com.avenhon.healthmaxxing.entity.User;
import com.avenhon.healthmaxxing.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static com.avenhon.healthmaxxing.mappers.UserMapper.toResponse;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse getUser(Authentication authentication) {
        return toResponse(userService.getUserByUsername(authentication.getName()));
    }
}
