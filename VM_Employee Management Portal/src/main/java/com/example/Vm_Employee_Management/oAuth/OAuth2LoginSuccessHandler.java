package com.example.Vm_Employee_Management.oAuth;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.Vm_Employee_Management.DAO.LoginObject;
import com.example.Vm_Employee_Management.Security.UserInfoUserDetails;
import com.example.Vm_Employee_Management.ServiceLayer.LoginObjectCreationService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private LoginObjectCreationService loginObjectCreationService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
            
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String email = oAuth2User.getEmail();
        
        // UserInfoUserDetails userDetails = 
        
        super.onAuthenticationSuccess(request, response, authentication);
    }
    
}
