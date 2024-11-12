package com.example.controller;


import com.example.dto.EmployeeInfoDto;
import com.example.dto.PartialUpdateDto;
import com.example.service.EmployeeInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/employees/")
public class EmployeeController {
    @Autowired
    EmployeeInfoService employeeInfoService;

    //Creating Employee
    @PostMapping(value = "/create")
    public ResponseEntity<?> createEmployee(@Validated @RequestBody EmployeeInfoDto employeeInfoDto) {
        String message = employeeInfoService.createEmployee(employeeInfoDto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //Getting All Employees
    @GetMapping(value = "/")
    public ResponseEntity<?> getAllEmployees() {
        List<EmployeeInfoDto> employees = employeeInfoService.getEmployees();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    //Getting employee by Id
    @GetMapping("/byId")
    public ResponseEntity<?> getEmployeeById(@RequestParam(value = "empId") Long empId) {
        EmployeeInfoDto employee = employeeInfoService.getEmpById(empId);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    //Updating the Employee
    @PutMapping(value = {"/update/{empId}"})
    public ResponseEntity<?> updateEmployee(@PathVariable(value = "empId") Long empId, @Validated
    @RequestBody EmployeeInfoDto employeeInfoDto) {
        String message = employeeInfoService.update(empId, employeeInfoDto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //Delete the Employee
    @DeleteMapping(value = {"/delete/{empId}"})
    public ResponseEntity<String> deleteEmployee(@PathVariable(value = "empId") Long empId) {
        String message = employeeInfoService.delete(empId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //Partially update the Employee details
    @PatchMapping(value = {"/partialUpdate/{empId}"})
    public ResponseEntity<?> partialUpdate(@PathVariable(value = "empId") Long empId, @RequestBody PartialUpdateDto partialUpdateDto) {
        List<String> list = employeeInfoService.partiallyUpdate(empId, partialUpdateDto);
        if (!list.isEmpty())
            return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>("Employee partially updated successfully!!", HttpStatus.OK);
    }
}
