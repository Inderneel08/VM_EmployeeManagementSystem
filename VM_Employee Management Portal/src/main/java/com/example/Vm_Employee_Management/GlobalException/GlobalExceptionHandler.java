package com.example.Vm_Employee_Management.GlobalException;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoHandlerFoundException(NoHandlerFoundException ex, Model model)
    {
        String errorMessage = "Sorry, the resource you requested is not available.";
        
        model.addAttribute("errorMessage", errorMessage);

        return("error.html");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleUnauthorizedAccessException(Model model)
    {
        String errorMessage = "You don't have permission to access this resource.";

        model.addAttribute("errorMessage", errorMessage);

        System.out.println("Hello World");

        return("error.html");
    }
    
}
