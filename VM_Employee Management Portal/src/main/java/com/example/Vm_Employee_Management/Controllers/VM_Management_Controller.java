package com.example.Vm_Employee_Management.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.Optional;

import com.example.Vm_Employee_Management.DAO.History;
import com.example.Vm_Employee_Management.DAO.VirtualMachines;
import com.example.Vm_Employee_Management.Repository.HistoryRepository;
import com.example.Vm_Employee_Management.Repository.VirtualMachineObjectRepository;


@RestController
public class VM_Management_Controller {

    @Autowired
    private VirtualMachineObjectRepository virtualMachineObjectRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @PostMapping("/create_vm")
    public ResponseEntity<?> createVm(@ModelAttribute VirtualMachines virtualMachines)
    {
        Optional<VirtualMachines> virtualmachines = virtualMachineObjectRepository.findByVMName(virtualMachines.getMachine_name());

        if(virtualmachines.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Virtual Machine with this name already exists");
        }

        if(virtualMachines.getAssigned_to().equals("")){
            virtualMachines.setAssigned_to(null);
        }

        virtualMachines.setDate_created(LocalDateTime.now());

        virtualMachineObjectRepository.save(virtualMachines);

        History history = new History();

        history.setVm_id(virtualMachineObjectRepository.findByVMName(virtualMachines.getMachine_name()).get().getId());

        history.setOperation_performed("VM CREATED");

        history.setOperation_performed_time(LocalDateTime.now());

        historyRepository.save(history);

        return(ResponseEntity.ok("Virtual Machine created"));
    }
}
