package com.example.plus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z0-9])[a-zA-Z0-9]{3,}$", message = "닉네임은 최소 3글자 이상, 알파벳 대소문자와 숫자로 구성해주세요.")
    private String username;

    @NotBlank
    @Pattern(regexp = "^.{4,}$", message = "비밀번호는 최소 4글자 이상 입력해주세요.")
    private String password;

    @NotBlank
    @Pattern(regexp = "^.{4,}$")
    private String checkpassword;
}
