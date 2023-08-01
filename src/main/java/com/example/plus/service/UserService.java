package com.example.plus.service;

import com.example.plus.dto.ApiResponseDto;
import com.example.plus.dto.LoginRequestDto;
import com.example.plus.dto.SignupRequestDto;
import com.example.plus.entity.User;
import com.example.plus.entity.UserRoleEnum;
import com.example.plus.jwt.JwtUtil;
import com.example.plus.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Getter
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    // 회원가입 API
    public ApiResponseDto signup(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();
        String checkpassword = signupRequestDto.getCheckpassword();
        UserRoleEnum role = signupRequestDto.getRole();



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

        String encodepassword = passwordEncoder.encode(password);

        User user = new User(username, encodepassword, role);
        userRepository.save(user);

        return new ApiResponseDto("회원가입에 성공하였습니다.", HttpStatus.CREATED.value());
    }


    // 로그인 API
    @Transactional
    public ApiResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        // 아이디 체크
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("닉네임 또는 패스워드를 확인해주세요")
        );

        // 비밀번호 체크
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new IllegalArgumentException("닉네임 또는 패스워드를 확인해주세요");
        }

        // 로그인
        httpServletResponse.addHeader(JwtUtil.AUTHORIZATION_HEADER,
                jwtUtil.createToken(user.getUsername(), user.getRole()));

        return new ApiResponseDto("로그인에 성공하셨습니다.", HttpStatus.CREATED.value());
    }
}
