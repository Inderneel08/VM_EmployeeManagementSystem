package com.example.Vm_Employee_Management.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Vm_Employee_Management.DAO.LoginObject;
import com.example.Vm_Employee_Management.ServiceLayer.LoginObjectCreationService;

import jakarta.validation.Valid;

@RestController
public class RegisterController {

    @Autowired
    private LoginObjectCreationService loginObjectCreationService;

    @PostMapping("/register")
    public ResponseEntity<?> afterRegister(@ModelAttribute @Valid LoginObject loginObject)
    {
        // Admin password admin123 user password user123
        
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        loginObject.setPassword(bCryptPasswordEncoder.encode(loginObject.getPassword()));

        loginObjectCreationService.createLoginObject(loginObject);

        return(ResponseEntity.ok("Registration successfull"));
    }

}
