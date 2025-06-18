package com.example.demo.service;

import com.example.demo.dto.PostWithCommentCountDto;
import com.example.demo.entity.Post;
import com.example.demo.entity.User;
import com.example.demo.repository.LikeRepository;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

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
    @Transactional
    public Post updatePost(Long postId, String title, String content, String email) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    
        if (!post.getWriterEmail().equals(email)) {
            throw new SecurityException("작성자만 수정할 수 있습니다.");
        }
        if(title != null && !title.isEmpty())
            post.setTitle(title);
        if(content != null && !content.isEmpty())
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

    //한개조회
    public Post getPostById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
    }

    @Transactional
    public List<PostWithCommentCountDto> getAllPostsWithCommentCount(String email) {
        Optional<User> user = userRepository.findByEmail(email);

        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream()
            .map(post -> PostWithCommentCountDto.of(post, getIsLiked(post.getId(), user.get().getId())))
            .toList();
    }

    private Boolean getIsLiked(Long postId, Long userId) {
        return likeRepository.findByUserEmailAndPostId(userId, postId).isPresent();
    }

    public List<Post> searchPosts(String keyword, LocalDateTime date){
        return postRepository.findAllBySearchAndCreatedDateDesc("%"+keyword+"%", date);
    }
}
