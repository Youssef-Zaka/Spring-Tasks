package com.sumerge.SpringPractice.Model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;





class ErrorResponseDtoTest {

    @Test
    void testErrorResponseDtoCreation() {
        // Arrange
        LocalDateTime timestamp = LocalDateTime.now();
        int status = 404;
        String error = "Not Found";
        String message = "The requested resource was not found";
        String path = "/api/resource";

        // Act
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(timestamp, status, error, message, path);

        // Assert
        assertEquals(timestamp, errorResponseDto.getTimestamp());
        assertEquals(status, errorResponseDto.getStatus());
        assertEquals(error, errorResponseDto.getError());
        assertEquals(message, errorResponseDto.getMessage());
        assertEquals(path, errorResponseDto.getPath());
    }

    @Test
    void testErrorResponseDtoSetters() {
        // Arrange
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(null, 0, null, null, null);
        LocalDateTime timestamp = LocalDateTime.now();
        int status = 500;
        String error = "Internal Server Error";
        String message = "An unexpected error occurred";
        String path = "/api/error";

        // Act
        errorResponseDto.setTimestamp(timestamp);
        errorResponseDto.setStatus(status);
        errorResponseDto.setError(error);
        errorResponseDto.setMessage(message);
        errorResponseDto.setPath(path);

        // Assert
        assertEquals(timestamp, errorResponseDto.getTimestamp());
        assertEquals(status, errorResponseDto.getStatus());
        assertEquals(error, errorResponseDto.getError());
        assertEquals(message, errorResponseDto.getMessage());
        assertEquals(path, errorResponseDto.getPath());
    }
}