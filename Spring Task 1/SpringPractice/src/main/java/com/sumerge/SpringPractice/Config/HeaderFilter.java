package com.sumerge.SpringPractice.Config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class HeaderFilter implements Filter {
    private static final List<String> EXCLUDED = List.of(
            "/swagger-ui", "/swagger-ui/", "/v3/api-docs", "/api-docs"
    );

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest  request  = (HttpServletRequest)  req;
        HttpServletResponse response = (HttpServletResponse) res;
        String path = request.getRequestURI();

        // Skip swagger
        if (EXCLUDED.stream().anyMatch(path::startsWith)) {
            chain.doFilter(req, res);
            return;
        }

        // Require header
        if ("true".equalsIgnoreCase(request.getHeader("x-validation-report"))) {
            chain.doFilter(req, res);
        } else {
            response.sendError(HttpServletResponse.SC_FORBIDDEN,
                    "Missing or invalid x-validation-report header");
        }
    }
}