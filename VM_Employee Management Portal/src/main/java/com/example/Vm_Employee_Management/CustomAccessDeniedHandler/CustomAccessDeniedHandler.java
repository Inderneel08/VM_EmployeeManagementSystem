package com.example.Vm_Employee_Management.CustomAccessDeniedHandler;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAccessDeniedHandler implements AccessDeniedHandler{

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
            
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication!=null){
                response.sendRedirect(request.getContextPath()+"/access-denied");
            }
    }
    
}
