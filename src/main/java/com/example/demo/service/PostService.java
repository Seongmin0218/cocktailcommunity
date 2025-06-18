package com.example.demo.service;

import com.example.demo.dto.PostWithCommentCountDto;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //게시글 생성
    public Post createPost(String title, String content, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setWriter(user);
        return postRepository.save(post);
    }
    
    //게시글 수정
    public Post updatePost(Long postId, String title, String content, String email) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    
        if (!post.getWriterEmail().equals(email)) {
            throw new SecurityException("작성자만 수정할 수 있습니다.");
        }
        
        post.setTitle(title);
        post.setContent(content);
        return postRepository.save(post);
    }
    
    //게시글 삭제
    public void deletePost(Long postId, String email) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        
        if (!post.getWriterEmail().equals(email)) {
            throw new SecurityException("작성자만 삭제할 수 있습니다.");
        }
    }

    //전체조회
    public List<Post> getAllPosts() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    //한개조회
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

    public List<PostWithCommentCountDto> getAllPostsWithCommentCount() {
    List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
    return posts.stream()
            .map(post -> new PostWithCommentCountDto(
                    post.getId(),
                    post.getTitle(),
                    post.getComments().size())) // 댓글 수
            .toList();
}
}
