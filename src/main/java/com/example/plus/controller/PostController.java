package com.example.plus.controller;

import com.example.plus.dto.ApiResponseDto;
import com.example.plus.dto.PostListResponseDto;
import com.example.plus.dto.PostRequestDto;
import com.example.plus.dto.PostResponseDto;
import com.example.plus.security.UserDetailsImpl;
import com.example.plus.service.PostService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {
    private final PostService postService;

    // 전체 게시글 목록 조회 API (제목, 작성자명, 작성날짜)
    @GetMapping("/posts")
    public List<PostListResponseDto> getPosts(){
        return postService.getPosts();
    }

    // 게시글 작성 API (제목, 작성)
    @PostMapping("/posts")
    public PostResponseDto createPost(@RequestBody PostRequestDto postRequestDto,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.createPost(postRequestDto, userDetails.getUser());
    }

    // 게시글 조회 API (제목, 작성자명, 작성날짜, 작성내용)
    @GetMapping("/posts/{id}")
    public PostResponseDto getPost(@PathVariable Long id){
        return postService.getPost(id);
    }

    // 게시글 수정 API (작성자만 수정가능)
    @PutMapping("/posts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id,
                                                     @RequestBody PostRequestDto postRequestDto,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.updatePost(id, postRequestDto, userDetails.getUser());
    }

    // 게시글 삭제 API (작성자만 삭제 가능)
    @DeleteMapping("/posts/{id}")
    public ApiResponseDto deletePost(@PathVariable Long id,
                                                     @AuthenticationPrincipal UserDetailsImpl userDetails){
        return postService.deletePost(id, userDetails.getUser());
    }

}
