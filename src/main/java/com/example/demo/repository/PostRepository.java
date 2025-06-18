package com.example.demo.repository;

import com.example.demo.entity.Post;
import java.time.LocalDateTime;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();

    @Query("select p from Post p where p.title like :keyword and FUNCTION('DATE', p.createdAt) = :date")
    List<Post> findAllBySearchAndCreatedDateDesc(@Param("keyword")String keyword, @Param("date") LocalDateTime date);
}
