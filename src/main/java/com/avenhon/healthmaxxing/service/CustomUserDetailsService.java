package com.avenhon.healthmaxxing.service;

import java.util.Collections;

import com.avenhon.healthmaxxing.exception.UserNotFoundByIdException;
import com.avenhon.healthmaxxing.exception.UserNotFoundException;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.avenhon.healthmaxxing.entity.User;
import com.avenhon.healthmaxxing.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  
  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

    if (user == null) {
      throw new UsernameNotFoundException("User not found with username " + username);
    }

    return new org.springframework.security.core.userdetails.User(
      user.getUsername(),
      user.getPassword(),
      user.getAuthorities()
    );
  }
}
