package com.example.Vm_Employee_Management.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.Vm_Employee_Management.DAO.LoginObject;
import com.example.Vm_Employee_Management.Repository.LoginObjectRepository;
import com.example.Vm_Employee_Management.Security.CustomAuthenticationFailureHandler;
import com.example.Vm_Employee_Management.Security.UserInfoUserDetailsService;

import jakarta.servlet.http.HttpSession;


@Controller
public class SignInController {

    @Autowired
    private UserInfoUserDetailsService userDetailsService;

    @Autowired
    private LoginObjectRepository loginObjectRepository;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @GetMapping("/login")
    public String login(Model model,HttpSession session)
    {
        String errorMessage = (String) session.getAttribute(CustomAuthenticationFailureHandler.ERROR_MESSAGE_KEY);

        model.addAttribute("login", new LoginObject());

        if(errorMessage!=null){
            model.addAttribute("errorMessage",errorMessage);

            session.removeAttribute(CustomAuthenticationFailureHandler.ERROR_MESSAGE_KEY);
        }

        return("Login.html");
    }

    @GetMapping("/home")
    public String loginSubmit(Model model)
    {        
        return("Homepage.html");
    }

}
