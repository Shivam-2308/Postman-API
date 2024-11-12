package com.example.repository;

//import model.EmployeeInfoModel;

import com.example.model.EmployeeInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeInfoRepo extends JpaRepository<EmployeeInfoModel, Long> {

    @Modifying
    @Query(value = "update employee_details ed set ed.email = :email where ed.id = :id", nativeQuery = true)
    int updateEmployeeEmail(@Param("id") Long id, @Param("email") String email);

}
