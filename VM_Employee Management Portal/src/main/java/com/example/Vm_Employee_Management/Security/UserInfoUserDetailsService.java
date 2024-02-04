package com.example.Vm_Employee_Management.Security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.Vm_Employee_Management.DAO.LoginObject;
import com.example.Vm_Employee_Management.Repository.LoginObjectRepository;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private LoginObjectRepository loginObjectRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Optional<LoginObject> loginObject =  loginObjectRepository.findByUsername(username);

        if(!(loginObject.isPresent())){
            throw new UsernameNotFoundException("User with this username does not exist");
        }

        return loginObject.map(UserInfoUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("Username "+username+"does not exists"));
    }

}
