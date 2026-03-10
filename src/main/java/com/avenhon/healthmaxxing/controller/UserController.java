package com.avenhon.healthmaxxing.controller;

import com.avenhon.healthmaxxing.dto.UserResponse;
import com.avenhon.healthmaxxing.mappers.UserMapper;
import com.avenhon.healthmaxxing.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.avenhon.healthmaxxing.mappers.UserMapper.toResponse;

@RestController
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    List<UserResponse> getUsers() {
        return userService.getUsers().stream().map(UserMapper::toResponse).toList();
    }

    @GetMapping("/user/{userId}")
    UserResponse getUserById(@PathVariable Long userId) {
        return toResponse(userService.getUserById(userId));
    }
}
