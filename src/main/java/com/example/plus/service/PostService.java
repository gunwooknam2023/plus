package com.example.plus.service;

import com.example.plus.dto.ApiResponseDto;
import com.example.plus.dto.PostListResponseDto;
import com.example.plus.dto.PostRequestDto;
import com.example.plus.dto.PostResponseDto;
import com.example.plus.entity.Post;
import com.example.plus.entity.User;
import com.example.plus.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;


    // 전체 게시글 목록 조회 API (제목, 작성자명, 작성날짜)

    public List<PostListResponseDto> getPosts() {
        List<Post> posts = postRepository.findAllByOrderByCreatedAtDesc();
        List<PostListResponseDto> postListResponseDtos = new ArrayList<>();

        for(Post post : posts){
            postListResponseDtos.add(new PostListResponseDto(post));
        }

        return postListResponseDtos;
    }



    // 게시글 작성 API (제목, 작성)
    public PostResponseDto createPost(PostRequestDto postRequestDto, User user) {
        if(user == null){
            throw new IllegalArgumentException("로그인 후 시도해주세요.");
        }

        Post post = new Post(postRequestDto, user);
        postRepository.save(post);

        return new PostResponseDto(post);
    }


    // 게시글 조회 API (제목, 작성자명, 작성날짜, 작성내용)

    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택하신 게시글이 존재하지 않습니다.")
        );

        return new PostResponseDto(post);
    }


    // 게시글 수정 API (작성자만 수정가능)
    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto postRequestDto, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택하신 게시글이 존재하지 않습니다.")
        );

        if(post.getUser().getId().equals(user.getId())){
            post.update(postRequestDto);
        } else {
            throw new IllegalArgumentException("작성자만 수정이 가능합니다.");
        }

        return new PostResponseDto(post);
    }


    // 게시글 삭제 API (작성자만 삭제 가능)
    @Transactional
    public ApiResponseDto deletePost(Long id, User user) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택하신 게시글이 존재하지 않습니다.")
        );

        if(post.getUser().getId().equals(user.getId())){
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException("작성자만 삭제가 가능합니다.");
        }

        return new ApiResponseDto("삭제가 완료되었습니다.", HttpStatus.CREATED.value());
    }

}
