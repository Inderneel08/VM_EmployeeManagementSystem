package com.example.Vm_Employee_Management.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {
    
    @GetMapping("/access-denied")
    public String getAccessDeniedException(Model model)
    {
        String errorMessage = "You don't have permission to access this resource.";

        model.addAttribute("errorMessage", errorMessage);

        return("error.html");
    }

}
