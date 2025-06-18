package com.example.demo.dto;

import com.example.demo.entity.Post;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PostWithCommentCountDto {
    private Long id;
    private String title;
    private int commentCount;
    @JsonProperty(namespace = "isLiked")
    private Boolean isLiked;

    public static PostWithCommentCountDto of(Post post, Boolean isLiked) {
        PostWithCommentCountDto dto = new PostWithCommentCountDto();
        dto.id = post.getId();
        dto.title = post.getTitle();
        dto.commentCount = post.getComments().size();
        dto.isLiked = isLiked;
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}