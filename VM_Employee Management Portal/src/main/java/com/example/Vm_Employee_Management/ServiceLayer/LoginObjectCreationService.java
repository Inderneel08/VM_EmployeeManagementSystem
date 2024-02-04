package com.example.Vm_Employee_Management.ServiceLayer;

import org.springframework.stereotype.Service;

import com.example.Vm_Employee_Management.DAO.LoginObject;
import com.example.Vm_Employee_Management.Repository.LoginObjectRepository;

@Service
public class LoginObjectCreationService implements LoginServices{

    private final LoginObjectRepository loginObjectRepository;


    public LoginObjectCreationService(LoginObjectRepository loginObjectRepository) {
        this.loginObjectRepository = loginObjectRepository;
    }


    @Override
    public boolean createLoginObject(LoginObject loginObject) {
        try {
            loginObjectRepository.save(loginObject);

            return true;
        } catch (Exception e) {
            e.printStackTrace();


            return false;
        }
    }
}
