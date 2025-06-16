package com.example.demo.controller;

import com.example.demo.dto.CommentRequest;
import com.example.demo.entity.Comment;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/comments")
    public ResponseEntity<?> writeComment(@RequestBody CommentRequest request, Principal principal) {
        String email = principal.getName();
        Comment saved = commentService.createComment(request.getPostId(), request.getContent(), email);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/api/comments{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id,
                                           @RequestBody CommentRequest request,
                                           Principal principal) {
        String email = principal.getName();
        Comment updated = commentService.updateComment(id, request.getContent(), email);
        return ResponseEntity.ok(updated);
    }
}
