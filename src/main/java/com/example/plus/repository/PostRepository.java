package com.example.plus.repository;

import com.example.plus.dto.PostListResponseDto;
import com.example.plus.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByCreatedAtDesc();
}
