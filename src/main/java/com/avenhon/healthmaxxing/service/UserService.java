package com.avenhon.healthmaxxing.service;

import com.avenhon.healthmaxxing.entity.User;
import com.avenhon.healthmaxxing.enums.Role;
import com.avenhon.healthmaxxing.exception.UserAlreadyExistsException;
import com.avenhon.healthmaxxing.exception.UserNotFoundByIdException;
import com.avenhon.healthmaxxing.exception.UserNotFoundException;
import com.avenhon.healthmaxxing.exception.UsernameAlreadyTakenException;
import com.avenhon.healthmaxxing.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundByIdException(userId));
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public String createUser(String email, String username, String rawPassword) throws UserAlreadyExistsException {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(email);
        }

        if (userRepository.existsByUsername(username)) {
            throw new UsernameAlreadyTakenException(username);
        }

        User user = new User(
                email,
                username,
                passwordEncoder.encode(rawPassword)
        );

        userRepository.save(user);
        return "User registered successfully!";
    }
}
