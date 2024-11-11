package com.example.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "employee_details")
//@Slf4j
//@XSlf4j
@Getter
@Setter
@NoArgsConstructor
public class EmployeeInfoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId;
    private String firstName;
    private String lastName;
    private String city;
    private String address;
    private Integer pincode;
    private String email;

}
