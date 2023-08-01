package com.example.plus.service;

import com.example.plus.dto.ApiResponseDto;
import com.example.plus.dto.CommentRequestDto;
import com.example.plus.dto.CommentResponseDto;
import com.example.plus.entity.Comment;
import com.example.plus.entity.Post;
import com.example.plus.entity.User;
import com.example.plus.repository.CommentRepository;
import com.example.plus.repository.PostRepository;
import com.example.plus.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    // 댓글 작성
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long id, User user) {
        if(user == null){
            throw new IllegalArgumentException("로그인 후 이용하세요.");
        }

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        Comment comment = new Comment(commentRequestDto, post, user);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto, Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        if(comment.getUser().getId().equals(user.getId())){
            comment.updateComment(commentRequestDto);
        } else {
            throw new IllegalArgumentException("댓글 작성자만 수정이 가능합니다.");
        }

        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    public ApiResponseDto deleteComment(Long id, User user) {
        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );

        if(comment.getUser().getId().equals(user.getId())){
            commentRepository.delete(comment);
        } else {
            throw new IllegalArgumentException("댓글 작성자만 삭제가 가능합니다.");
        }

        return new ApiResponseDto("삭제가 완료되었습니다.", HttpStatus.CREATED.value());
    }
}
