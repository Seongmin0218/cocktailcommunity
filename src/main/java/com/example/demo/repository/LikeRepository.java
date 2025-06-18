package com.example.demo.repository;

import com.example.demo.entity.Like;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("select l from Like l where l.user.id = :userId and l.post.id =:postId")
    Optional<Like> findByUserEmailAndPostId(@Param("userId") Long userId, @Param("postId")Long postId);
}
