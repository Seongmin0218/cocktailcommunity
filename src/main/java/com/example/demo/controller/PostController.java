package com.example.demo.controller;

import com.example.demo.dto.PostRequest;
import com.example.demo.entity.Post;
import com.example.demo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //게시글쓰기
    @PostMapping("/api/posts")
    public ResponseEntity<?> writePost(@RequestBody PostRequest request, Principal principal) {
        String email = principal.getName(); // Jwt에서 가져온 사용자 이메일
        Post saved = postService.createPost(request.getTitle(), request.getContent(), email);
        return ResponseEntity.ok(saved);
    }

    //게시글수정
    @PutMapping("/api/posts/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id,
                                        @RequestBody PostRequest request,
                                        Principal principal) {
        String email = principal.getName();
        Post updated = postService.updatePost(id, request.getTitle(), request.getContent(), email);
        return ResponseEntity.ok(updated);
    }

    //게시글삭제
    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id, Principal principal) {
        String email = principal.getName();
        postService.deletePost(id, email);
        return ResponseEntity.ok("삭제되었습니다.");
    }

    //조회
    @GetMapping("/api/posts")
    public ResponseEntity<?> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/api/posts/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }
}

