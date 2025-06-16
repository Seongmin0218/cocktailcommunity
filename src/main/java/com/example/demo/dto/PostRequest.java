package com.example.demo.dto;

import lombok.*;

@Getter
@Setter
@Data
public class PostRequest {
    private String title;
    private String content;
}
