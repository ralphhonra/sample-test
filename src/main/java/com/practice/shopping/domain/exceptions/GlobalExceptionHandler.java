package com.practice.shopping.domain.exceptions;

import com.practice.shopping.domain.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFound(ResourceNotFoundException err) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("GE: Requested item not found!", err.getMessage()));
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ApiResponse> handleAlreadyExist(AlreadyExistException err) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponse("GE: Requested item already exist!", err.getMessage()));
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiResponse> handleSqlException(SQLException err) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("GE: Internal server error.", err.getMessage()));
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ApiResponse> handleIOException(IOException err) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("GE: An error occurred!", err.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception err) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("GE: An error occurred!", err.getMessage()));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiResponse> handleNullPointer(NullPointerException err) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("GE: An error occurred!", err.getMessage()));
    }
}
