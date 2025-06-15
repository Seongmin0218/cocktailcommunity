package com.example.demo.controller;

import com.example.demo.dto.UserLoginRequestDTO;
import com.example.demo.dto.UserSignupRequestDTO;
import com.example.demo.service.UserLoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserLoginController {
    private final UserLoginService userLoginService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid UserSignupRequestDTO dto) {
        userLoginService.signup(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequestDTO dto) {
        String token = userLoginService.login(dto);
        return ResponseEntity.ok(token);
    }

}
