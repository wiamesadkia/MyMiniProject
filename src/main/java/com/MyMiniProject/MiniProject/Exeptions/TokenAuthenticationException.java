package com.MyMiniProject.MiniProject.Exeptions;

import com.MyMiniProject.MiniProject.Models.Responses.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TokenAuthenticationException extends RuntimeException implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorResponse errorDetails = ErrorResponse.builder()
                .errorCode(HttpStatus.UNAUTHORIZED.value())
                .errorMessage(authException.getMessage())
                .errorDetails("Unauthorized access or token expired")
                .build();
        ObjectMapper objectMapper=new ObjectMapper();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        objectMapper.writeValue(response.getOutputStream(),errorDetails);
    }
}

