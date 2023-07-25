package com.example.plus.error;

import com.example.plus.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<ApiResponseDto> IllegalArgumentExceptionHandler(IllegalArgumentException ex){
        ApiResponseDto apiResponseDto = new ApiResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<ApiResponseDto> NullPointerExceptionHandler(IllegalArgumentException ex){
        ApiResponseDto apiResponseDto = new ApiResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(apiResponseDto, HttpStatus.BAD_REQUEST);
    }
}
