package com.example.plus.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiResponseDto {
    private String msg;
    private Integer code;

    public ApiResponseDto(String msg, Integer code){
        this.msg = msg;
        this.code = code;
    }
}
