package com.example.Vm_Employee_Management.Security;

import java.io.IOException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler{

    public static final String ERROR_MESSAGE_KEY = "authenticationErrorMessage";

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
            
        UrlPathHelper helper = new UrlPathHelper();

        String contextPath = helper.getContextPath(request);

        String errorMessage = "Incorrect Credentials";

        request.getSession().setAttribute(ERROR_MESSAGE_KEY,errorMessage);

        response.sendRedirect(contextPath+"/login");
    }

}
