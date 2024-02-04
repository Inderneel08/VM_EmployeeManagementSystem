package com.example.Vm_Employee_Management.Repository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Vm_Employee_Management.DAO.LoginObject;

@Repository
public interface LoginObjectRepository extends JpaRepository<LoginObject,BigInteger> {
    
    @Query(value = "select * from login where login.username = :username",nativeQuery = true)
    Optional<LoginObject> findByUsername(@Param("username") String username);

} 
