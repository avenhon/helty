package com.avenhon.healthmaxxing.controller;

import com.avenhon.healthmaxxing.dto.CreateUserRequest;
import com.avenhon.healthmaxxing.entity.User;
import com.avenhon.healthmaxxing.security.JwtUtil;
import com.avenhon.healthmaxxing.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtils;

    public AuthenticationController(
            AuthenticationManager authenticationManager,
            UserService userService,
            JwtUtil jwtUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public String authenticateUser(@RequestBody CreateUserRequest user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.username(),
                        user.password()
                )
        );

        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        assert userDetails != null;
        return jwtUtils.generateToken(userDetails.getUsername());
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String registerUser(@RequestBody User user) {
        return userService.createUser(user.getEmail(), user.getUsername(), user.getPassword());
    }
}
