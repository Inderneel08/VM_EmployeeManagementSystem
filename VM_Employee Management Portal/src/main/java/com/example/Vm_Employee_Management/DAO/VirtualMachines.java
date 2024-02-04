package com.example.Vm_Employee_Management.DAO;

import java.math.BigInteger;
import java.time.LocalDateTime;



// import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "virtualmachines")
public class VirtualMachines {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private BigInteger id;

    @Column(name = "assigned_to")
    private String assigned_to;

    @Column(name = "machine_name")
    private String machine_name;

    @Column(name = "date_created")
    private LocalDateTime date_created;

    @Column(name = "status")
    private int status;
}
