package com.example.Vm_Employee_Management.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;
import com.example.Vm_Employee_Management.DAO.VirtualMachines;
import java.util.List;


@Repository
public interface VirtualMachineObjectRepository extends JpaRepository<VirtualMachines,BigInteger>{
    
    @Query(value = "select * from virtualmachines where virtualmachines.machine_name = :vmName",nativeQuery = true)
    Optional<VirtualMachines> findByVMName(@Param("vmName") String vmName);
    
    @Query(value = "select * from virtualmachines where virtualmachines.id = :id",nativeQuery = true)
    Optional<VirtualMachines> findById(@Param("id") String id);

}
