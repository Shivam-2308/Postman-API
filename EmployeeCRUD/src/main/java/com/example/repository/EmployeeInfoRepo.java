package com.example.repository;

//import model.EmployeeInfoModel;
import com.example.model.EmployeeInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeInfoRepo extends JpaRepository<EmployeeInfoModel,Long> {

}
