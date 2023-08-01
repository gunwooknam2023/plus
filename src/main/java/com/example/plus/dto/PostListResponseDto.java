package com.example.plus.dto;

import com.example.plus.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponseDto {
    private Long id;
    private String title; // 제목
    private String username; // 작성자
    private LocalDateTime createAt; // 생성시간
    private LocalDateTime modifiedAt; // 수정시간

    public PostListResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.username = post.getUser().getUsername();
        this.createAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
    }
}
