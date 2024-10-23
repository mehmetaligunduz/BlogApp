package com.scalefocus.blogapp.resource;

import com.scalefocus.blogapp.model.LoginRequest;
import com.scalefocus.blogapp.model.LoginResponse;
import com.scalefocus.blogapp.model.RegisterRequest;
import com.scalefocus.blogapp.model.RegisterResponse;
import com.scalefocus.blogapp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity
                .ok(userService
                        .register(registerRequest)
                );
    }

    @GetMapping("/callback")
    public ResponseEntity<String> handleCallback() {
        return ResponseEntity.ok("Callback received");
    }


}
