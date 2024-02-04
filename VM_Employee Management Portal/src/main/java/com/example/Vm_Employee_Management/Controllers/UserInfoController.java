package com.example.Vm_Employee_Management.Controllers;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.Vm_Employee_Management.DAO.LoginObject;
import com.example.Vm_Employee_Management.Repository.LoginObjectRepository;
import com.example.Vm_Employee_Management.Security.UserInfoUserDetails;
import com.example.Vm_Employee_Management.ServiceLayer.LoginObjectCreationService;
import jakarta.validation.Valid;

@Controller
public class UserInfoController {

    private final LoginObjectRepository loginObjectRepository;

    public UserInfoController(LoginObjectRepository loginObjectRepository,LoginObjectCreationService loginObjectCreationService) {
        this.loginObjectRepository = loginObjectRepository;
    }

    @GetMapping("/myDetails")
    public String getDetails(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String username = ((UserInfoUserDetails) authentication.getPrincipal()).getUsername();

        Optional<LoginObject> loginObject = loginObjectRepository.findByUsername(username);

        model.addAttribute("loginObject", loginObject);

        return("UserInfo.html");
    }

    @PostMapping("/saveMyDetails")
    public String saveUserDetails(@ModelAttribute @Valid LoginObject loginObject)
    {
        System.out.println(loginObject);

        return("redirect:/home");
    }

    @GetMapping("/changePassword")
    public String changePassword(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        String username = ((UserInfoUserDetails) authentication.getPrincipal()).getUsername();

        Optional<LoginObject> loginObject = loginObjectRepository.findByUsername(username);

        model.addAttribute("loginObject", loginObject);

        return("changePassword.html");
    }

    @PostMapping("/changePassword")
    public String savePassword(@ModelAttribute @Valid LoginObject loginObject)
    {
        System.out.println(loginObject.getUsername());

        return("redirect:/home");
    }

    @GetMapping("/create_user")
    public String createUser(Model model)
    {
        model.addAttribute("login", new LoginObject());

        return("create_users.html");
    }

}
