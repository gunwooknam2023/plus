package com.example.plus.service;

import com.example.plus.dto.ApiResponseDto;
import com.example.plus.dto.SignupRequestDto;
import com.example.plus.entity.User;
import com.example.plus.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    // 회원가입 API
    @Transactional
    public ApiResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String checkpassword = signupRequestDto.getCheckpassword();

        // 닉네임 중복 확인
        if(userRepository.findByUsername((username)).isPresent()){
            throw new IllegalArgumentException("중복된 닉네임 입니다.");
        }

        // 비밀번호 체크
        if(!password.equals(checkpassword)){
            throw new IllegalArgumentException("비밀번호, 비밀번호 확인칸에 동일한 비밀번호를 작성해주세요.");
        }

        // 아이디, 비밀번호 체크
        if(username.contains(password)){
            throw new IllegalArgumentException("아이디와 비밀번호는 동일하게 설정할 수 없습니다.");
        }

        User user = new User(username, password);
        userRepository.save(user);

        return new ApiResponseDto("회원가입에 성공하였습니다.", HttpStatus.CREATED.value());
    }
}
