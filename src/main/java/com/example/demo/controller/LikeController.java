package com.example.demo.controller;

import com.example.demo.service.LikeService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/api/like/{postId}")
    public ResponseEntity<?> like(@PathVariable Long postId, Principal principal) {
        String email = principal.getName();
        return ResponseEntity.ok().body(likeService.like(email, postId));
    }

}
