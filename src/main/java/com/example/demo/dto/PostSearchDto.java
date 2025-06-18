package com.example.demo.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostSearchDto {
    private String keyword;
    private LocalDateTime date;
}
