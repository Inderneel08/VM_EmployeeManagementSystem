package com.example.Vm_Employee_Management.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import com.example.Vm_Employee_Management.DAO.History;

@Repository
public interface HistoryRepository extends JpaRepository<History,BigInteger> {
    
}
