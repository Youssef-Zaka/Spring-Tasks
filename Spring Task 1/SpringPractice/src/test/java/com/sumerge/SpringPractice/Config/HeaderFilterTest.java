package com.sumerge.SpringPractice.Config;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.io.IOException;
import static org.mockito.Mockito.*;


class HeaderFilterTest {

    private HeaderFilter headerFilter;
    private HttpServletRequest mockRequest;
    private HttpServletResponse mockResponse;
    private FilterChain mockChain;

    @BeforeEach
    void setUp() {
        headerFilter = new HeaderFilter();
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
        mockChain = mock(FilterChain.class);
    }

    @Test
    void testDoFilter_ExcludedPath() throws IOException, jakarta.servlet.ServletException {
        when(mockRequest.getRequestURI()).thenReturn("/swagger-ui");

        headerFilter.doFilter(mockRequest, mockResponse, mockChain);

        verify(mockChain, times(1)).doFilter(mockRequest, mockResponse);
        verify(mockResponse, never()).sendError(anyInt(), anyString());
    }

    @Test
    void testDoFilter_ValidHeader() throws IOException, jakarta.servlet.ServletException {
        when(mockRequest.getRequestURI()).thenReturn("/some-path");
        when(mockRequest.getHeader("x-validation-report")).thenReturn("true");

        headerFilter.doFilter(mockRequest, mockResponse, mockChain);

        verify(mockChain, times(1)).doFilter(mockRequest, mockResponse);
        verify(mockResponse, never()).sendError(anyInt(), anyString());
    }

    @Test
    void testDoFilter_MissingHeader() throws IOException, jakarta.servlet.ServletException {
        when(mockRequest.getRequestURI()).thenReturn("/some-path");
        when(mockRequest.getHeader("x-validation-report")).thenReturn(null);

        headerFilter.doFilter(mockRequest, mockResponse, mockChain);

        verify(mockChain, never()).doFilter(mockRequest, mockResponse);
        verify(mockResponse, times(1)).sendError(HttpServletResponse.SC_FORBIDDEN,
                "Missing or invalid x-validation-report header");
    }

    @Test
    void testDoFilter_InvalidHeader() throws IOException, jakarta.servlet.ServletException {
        when(mockRequest.getRequestURI()).thenReturn("/some-path");
        when(mockRequest.getHeader("x-validation-report")).thenReturn("false");

        headerFilter.doFilter(mockRequest, mockResponse, mockChain);

        verify(mockChain, never()).doFilter(mockRequest, mockResponse);
        verify(mockResponse, times(1)).sendError(HttpServletResponse.SC_FORBIDDEN,
                "Missing or invalid x-validation-report header");
    }
}