package com.sumerge.SpringPractice.Config;
import com.sumerge.SpringPractice.Exception.ResourceNotFoundException;
import com.sumerge.SpringPractice.Model.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;





class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler globalExceptionHandler;
    private HttpServletRequest mockRequest;

    @BeforeEach
    void setUp() {
        globalExceptionHandler = new GlobalExceptionHandler();
        mockRequest = Mockito.mock(HttpServletRequest.class);
    }

    @Test
    void testHandleResourceNotFound() {
        // Arrange
        ResourceNotFoundException exception = new ResourceNotFoundException("Resource not found");
        when(mockRequest.getRequestURI()).thenReturn("/test/resource");

        // Act
        ResponseEntity<ErrorResponseDto> responseEntity = globalExceptionHandler.handleResourceNotFound(exception, mockRequest);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ErrorResponseDto responseBody = responseEntity.getBody();
        assertEquals(HttpStatus.NOT_FOUND.value(), responseBody.getStatus());
        assertEquals("Not Found", responseBody.getError());
        assertEquals("Resource not found", responseBody.getMessage());
        assertEquals("/test/resource", responseBody.getPath());
    }

    @Test
    void testHandleGeneralException() {
        // Arrange
        Exception exception = new Exception("General error occurred");
        when(mockRequest.getRequestURI()).thenReturn("/test/general");

        // Act
        ResponseEntity<ErrorResponseDto> responseEntity = globalExceptionHandler.handleGeneralException(exception, mockRequest);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        ErrorResponseDto responseBody = responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseBody.getStatus());
        assertEquals("Internal Server Error", responseBody.getError());
        assertEquals("General error occurred", responseBody.getMessage());
        assertEquals("/test/general", responseBody.getPath());
    }
}