package com.example.Vm_Employee_Management.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.math.BigInteger;
import com.example.Vm_Employee_Management.DAO.History;
import com.example.Vm_Employee_Management.DAO.LoginObject;
import com.example.Vm_Employee_Management.DAO.VirtualMachines;
import com.example.Vm_Employee_Management.Repository.HistoryRepository;
import com.example.Vm_Employee_Management.Repository.LoginObjectRepository;
import com.example.Vm_Employee_Management.Repository.VirtualMachineObjectRepository;
import com.example.Vm_Employee_Management.Security.UserInfoUserDetails;
import com.example.Vm_Employee_Management.ServiceLayer.LoginObjectCreationService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class DataController {

    @Autowired
    private LoginObjectRepository loginObjectRepository;

    @Autowired
    private LoginObjectCreationService loginObjectCreationService;

    @Autowired
    private VirtualMachineObjectRepository virtualMachineObjectRepository;

    @Autowired
    private HistoryRepository historyRepository;


    @GetMapping("/getUsers")
    public ResponseEntity<?> getListOfUsers(HttpServletRequest request)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = ((UserInfoUserDetails) authentication.getPrincipal()).getUsername();

        List<LoginObject> loginObject = loginObjectRepository.findAll()
        .stream().filter(user -> !user.getUsername().equals(username))
        .collect(Collectors.toList());

        return(ResponseEntity.ok(loginObject));
    }

    @GetMapping("/getVMS")
    public ResponseEntity<?> getListofVms()
    {
        List<VirtualMachines> listofVirtualMachines = virtualMachineObjectRepository.findAll()
        .stream().collect(Collectors.toList());

        return(ResponseEntity.ok(listofVirtualMachines));
    }


    @GetMapping("/getVmS")
    public ResponseEntity<?> getListOfVirtualMachines()
    {
        List<VirtualMachines> virtualmachines = virtualMachineObjectRepository.findAll();

        return(ResponseEntity.ok(virtualmachines));
    }

    @PostMapping("/create_user")
    public ResponseEntity<?> saveUser(@ModelAttribute LoginObject loginObject)
    {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        loginObject.setPassword(bCryptPasswordEncoder.encode(loginObject.getPassword()));

        boolean userCreated = loginObjectCreationService.createLoginObject(loginObject);

        if(userCreated){
            return ResponseEntity.ok("User created successfully!");
        }
        else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create user");
        }
    }

    @PostMapping("/updateUserRole")
    public ResponseEntity<?> updateUserRole(@RequestBody Map<String,String> requestBody)
    {
        Optional<LoginObject> loginObject = loginObjectRepository.findByUsername(requestBody.get("username"));

        if(loginObject.isPresent()){
            LoginObject loginObject2 = loginObject.get();

            loginObject2.setRole(requestBody.get("newRole"));

            loginObjectRepository.save(loginObject2);

            return ResponseEntity.ok("Role updated successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PostMapping("/updateUserStatus")
    public ResponseEntity<?> updateUserStatus(@RequestBody Map<String,String> requestBody)
    {
        Optional<LoginObject> loginObject = loginObjectRepository.findByUsername(requestBody.get("username"));

        if(loginObject.isPresent()){
            LoginObject loginObject2 = loginObject.get();

            loginObject2.setStatus(Integer.parseInt(requestBody.get("newStatus")));

            loginObjectRepository.save(loginObject2);

            return ResponseEntity.ok("Status updated successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }

    @PostMapping("/updateVMStatus")
    public ResponseEntity<?> updateVmStatus(@RequestBody Map<String,String> requestBody)
    {
        Optional<VirtualMachines> virtualMachine = virtualMachineObjectRepository.findById(requestBody.get("id"));

        if(virtualMachine.isPresent()){
            VirtualMachines virtualmachine = virtualMachine.get();

            virtualmachine.setStatus(Integer.parseInt(requestBody.get("newStatus")));

            virtualMachineObjectRepository.save(virtualmachine);

            History history = new History();

            if(virtualmachine.getStatus()==1){
                history.setOperation_performed("ACTIVATED");
            }
            else{
                history.setOperation_performed("INACTIVATED");
            }

            history.setVm_id(BigInteger.valueOf((Long.parseLong(requestBody.get("id")))));

            history.setOperation_performed_time(LocalDateTime.now());

            historyRepository.save(history);

            return ResponseEntity.ok("Status updated successfully");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("VM not found");
    }


    @PostMapping("/updateAssignedToStatus")
    public ResponseEntity<?> updateAssignedToStatus(@RequestBody Map<String,String> requestBody)
    {
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("VM not found");        
    }

}
