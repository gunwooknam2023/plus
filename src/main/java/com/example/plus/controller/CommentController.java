package com.example.plus.controller;


import com.example.plus.dto.ApiResponseDto;
import com.example.plus.dto.CommentRequestDto;
import com.example.plus.dto.CommentResponseDto;
import com.example.plus.security.UserDetailsImpl;
import com.example.plus.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;


    // 댓글 작성
    @PostMapping("/comment/{id}")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto,
                                            @PathVariable Long id,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(commentRequestDto, id, userDetails.getUser());
    }

    // 댓글 수정
    @PutMapping("/comment/{id}")
    public CommentResponseDto updateComment(@RequestBody CommentRequestDto commentRequestDto,
                                           @PathVariable Long id,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(commentRequestDto, id, userDetails.getUser());
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{id}")
    public ApiResponseDto deleteComment(@PathVariable Long id,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(id, userDetails.getUser());
    }

}
