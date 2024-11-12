package com.example.service;


import com.example.constants.ApiMessage;
import com.example.dto.EmployeeInfoDto;

import com.example.dto.PartialUpdateDto;
import com.example.exception.ApiException;
import com.example.model.EmployeeInfoModel;
import com.example.repository.EmployeeInfoRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeInfoService {
    @Autowired
    private EmployeeInfoRepo employeeInfoRepo;
    @Autowired
    private ModelMapper modelMapper;

    //Creating Employees
    public String createEmployee(EmployeeInfoDto employeeInfoDto) {
        System.out.println("Creating the employee!!");
        if (null == employeeInfoDto) {
            System.out.println("employee data cannot be null/empty");
            throw new ApiException("Employee " + ApiMessage.DATA_CANNOT_BE_NULL, HttpStatus.BAD_REQUEST);
        }
        employeeInfoRepo.save(this.setEmployeeModel(employeeInfoDto, new EmployeeInfoModel()));
        return "Employee created successfully!!";
    }

    //Getting all employees
    public List<EmployeeInfoDto> getEmployees() {
        System.out.println("Getting all the employees");
        List<EmployeeInfoModel> allEmployees = employeeInfoRepo.findAll();
        if (null == allEmployees || allEmployees.isEmpty()) {
            System.out.println("Employees not found!!");
            throw new ApiException("Employees " + ApiMessage.NOT_FOUND, HttpStatus.NO_CONTENT);
        }
        List<EmployeeInfoDto> employeesList = new ArrayList<>();
        for (EmployeeInfoModel employee : allEmployees)
            employeesList.add(convertToEmpInfoDto(employee));
        return employeesList;
    }

    //Getting Employee By Id
    public EmployeeInfoDto getEmpById(Long empId) {
        System.out.println("Getting employee by Id");
        EmployeeInfoModel emp = employeeInfoRepo.findById(empId).
                orElseThrow(() -> {
                    System.out.println("Employee not exist for id " + empId);
                    return new ApiException("Employee with " + empId + ApiMessage.NOT_EXIST, HttpStatus.NOT_FOUND);
                });
        return this.convertToEmpInfoDto(emp);
    }

    //Updating The Employee
    @Transactional
    public String update(Long empId, EmployeeInfoDto empDto) {
        System.out.println("Updating the employee");
        if (null == empDto) {
            System.out.println("Employee data cannot be null/empty");
            throw new ApiException("Employee " + ApiMessage.DATA_CANNOT_BE_NULL, HttpStatus.BAD_REQUEST);
        }
        EmployeeInfoModel empModel = employeeInfoRepo.findById(empId)
                .orElseThrow(() -> {
                    System.out.println("Employee not exist for id " + empId);
                    return new ApiException("Employee with " + empId + ApiMessage.NOT_EXIST, HttpStatus.NOT_FOUND);
                });
        employeeInfoRepo.save(this.setEmployeeModel(empDto, empModel));
        return "Employee updated successfully!!";
    }

    //Deleting the employee
    public String delete(Long empId) {
        System.out.println("Deleteing the Employee");
        boolean exists = employeeInfoRepo.existsById(empId);
        if (!exists) {
            System.out.println("Employee not exists for id "+empId);
            throw new ApiException("Employee with " + empId + ApiMessage.NOT_EXIST, HttpStatus.NOT_FOUND);
        }
        employeeInfoRepo.deleteById(empId);
        return "Successfully deleted!!";
    }

    //Partially updating the Employee Details
    public List<String> partiallyUpdate(Long empId, PartialUpdateDto partialUpdateDto) {
        System.out.println("Partially Updating!!");
        List<String> list = new ArrayList<>();
        if (null == partialUpdateDto || null == empId)
            throw new ApiException(ApiMessage.NOT_NULL, HttpStatus.BAD_REQUEST);
        EmployeeInfoModel employeeInfoModel = employeeInfoRepo.findById(empId)
                .orElseThrow(() -> new ApiException(ApiMessage.NOT_EXIST, HttpStatus.NOT_FOUND));
        if (null != partialUpdateDto.getFirstName()) {
            if (!partialUpdateDto.getFirstName().isBlank())
                employeeInfoModel.setFirstName(partialUpdateDto.getFirstName());
            else
                list.add("firstName can't be empty");
        }
        if (null != partialUpdateDto.getLastName()) {
            if (!partialUpdateDto.getLastName().isBlank())
                employeeInfoModel.setLastName(partialUpdateDto.getLastName());
            else
                list.add("latstName can't be empty");
        }
        if (null != partialUpdateDto.getCity()) {
            if (!partialUpdateDto.getCity().isBlank())
                employeeInfoModel.setCity(partialUpdateDto.getCity());
            else
                list.add("city can't be empty");
        }
        if (null != partialUpdateDto.getAddress()) {
            if (!partialUpdateDto.getAddress().isBlank())
                employeeInfoModel.setAddress(partialUpdateDto.getAddress());
            else
                list.add("address cannot be empty!");
        }
        if (null != partialUpdateDto.getEmail()) {
            if (!partialUpdateDto.getEmail().isBlank())
                employeeInfoModel.setEmail(partialUpdateDto.getEmail());
            else
                list.add("email can't be empty");
        }
        if (null != partialUpdateDto.getPincode())
            employeeInfoModel.setPincode(partialUpdateDto.getPincode());
        employeeInfoRepo.save(employeeInfoModel);
        return list;
    }

    //Converting EmpInfoModel to EmpInfoDto
    public EmployeeInfoDto convertToEmpInfoDto(EmployeeInfoModel employeeInfoModel) {
        return modelMapper.map(employeeInfoModel, EmployeeInfoDto.class);
    }

    private EmployeeInfoModel setEmployeeModel(EmployeeInfoDto empDTO, EmployeeInfoModel model) {
        model.setFirstName(empDTO.getFirstName());
        model.setLastName(empDTO.getLastName());
        model.setAddress(empDTO.getAddress());
        model.setCity(empDTO.getCity());
        model.setPincode(empDTO.getPincode());
        model.setEmail(empDTO.getEmail());
        return model;
    }
}
