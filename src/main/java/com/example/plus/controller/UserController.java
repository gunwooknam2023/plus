package com.example.plus.controller;

import com.example.plus.dto.ApiResponseDto;
import com.example.plus.dto.SignupRequestDto;
import com.example.plus.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    // 회원가입 API
    @PostMapping("/user/signup")
    public ResponseEntity<ApiResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto){
        userService.signup(signupRequestDto);
        return ResponseEntity.ok().body(new ApiResponseDto("회원가입에 성공했습니다.", HttpStatus.CREATED.value()));
    }





}
