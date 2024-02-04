package com.example.Vm_Employee_Management.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.Vm_Employee_Management.DAO.VirtualMachines;

@Controller
public class Views {
    
    @GetMapping("/create_vm")
    public String createVm(Model model)
    {
        model.addAttribute("vmDetails", new VirtualMachines());

        return("create_vm.html");
    }
}
