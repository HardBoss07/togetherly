package ch.bosshard.matteo.togetherly.controllers;

import ch.bosshard.matteo.togetherly.classes.auth.LoginRequest;
import ch.bosshard.matteo.togetherly.classes.auth.SignupRequest;
import ch.bosshard.matteo.togetherly.classes.entity.User;
import ch.bosshard.matteo.togetherly.classes.repository.UserRepository;
import ch.bosshard.matteo.togetherly.classes.util.ApiResponse;
import ch.bosshard.matteo.togetherly.classes.util.JwtResponse;
import ch.bosshard.matteo.togetherly.classes.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            logger.warn("Signup attempt with existing username: {}", request.getUsername());
            return ResponseEntity.badRequest().body(new ApiResponse("Username already taken"));
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setHashedPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername());
        logger.info("User registered successfully: {}", user.getUsername());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());
        if (userOpt.isEmpty() || !passwordEncoder.matches(request.getPassword(), userOpt.get().getHashedPassword())) {
            logger.warn("Invalid login attempt for username: {}", request.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("Invalid credentials"));
        }

        String token = jwtUtil.generateToken(userOpt.get().getUsername());
        logger.info("User logged in: {}", userOpt.get().getUsername());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}