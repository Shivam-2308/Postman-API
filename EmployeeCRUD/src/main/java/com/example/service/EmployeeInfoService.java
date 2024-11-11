package com.example.service;


import com.example.dto.EmployeeInfoDto;

import com.example.dto.PartialUpdateDto;
import com.example.model.EmployeeInfoModel;
import com.example.repository.EmployeeInfoRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public boolean createEmployee(EmployeeInfoDto employeeInfoDto) {
        System.out.println("Creating the employee!!");
        if (null == employeeInfoDto)
            return false;
        EmployeeInfoModel model = new EmployeeInfoModel();
        model.setFirstName(employeeInfoDto.getFirstName());
        model.setLastName(employeeInfoDto.getLastName());
        model.setAddress(employeeInfoDto.getAddress());
        model.setCity(employeeInfoDto.getCity());
        model.setPincode(employeeInfoDto.getPincode());
        model.setEmail(employeeInfoDto.getEmail());
        employeeInfoRepo.save(model);
        return true;
    }

    //Getting all employees
    public List<EmployeeInfoDto> getEmployees() {
        List<EmployeeInfoDto> employeesList = new ArrayList<>();
        List<EmployeeInfoModel> allEmployees = employeeInfoRepo.findAll();
        if (null == allEmployees || allEmployees.isEmpty()) {
            System.out.println("No employee exist.");
            return Collections.emptyList();
        }
        for (EmployeeInfoModel employee : allEmployees) {
            employeesList.add(convertToEmpInfoDto(employee));
        }
        return employeesList;
    }

    //Getting Employee By Id
    public EmployeeInfoDto getEmpById(Long empId) {
        Optional<EmployeeInfoModel> employee = employeeInfoRepo.findById(empId);
        if (employee.isPresent()) {
            return employee.map(this::convertToEmpInfoDto)
                    .orElse(null);
        }
        System.out.println("Employee not found with id: " + empId);
        return null;
    }

    //Updating The Employee
    public boolean update(Long empId, EmployeeInfoDto empDto) {
        System.out.println("Updating the employee");
        if (null == empDto)
            throw new RuntimeException("dto is null");
        Optional<EmployeeInfoModel> empOptional = employeeInfoRepo.findById(empId);
        if (empOptional.isPresent()) {
            EmployeeInfoModel empModel = empOptional.get();
            empModel.setFirstName(empDto.getFirstName());
            empModel.setLastName(empDto.getLastName());
            empModel.setCity(empDto.getCity());
            empModel.setAddress(empDto.getAddress());
            empModel.setPincode(empDto.getPincode());
            empModel.setEmail(empDto.getEmail());
            employeeInfoRepo.save(empModel);
            return true;
        }
        return false;
    }

    //Deleting the employee
    public String delete(Long empId) {
        System.out.println("Deleteing the Employee");
        employeeInfoRepo.deleteById(empId);
        return "Successfully deleted!!";
    }

    //Partially updating the Emplyee Details
    public List<String> partiallyUpdate(Long empId, PartialUpdateDto partialUpdateDto) {
        System.out.println("Partially Updating!!");
        List<String> list = new ArrayList<>();
        if (null == partialUpdateDto)
            throw new RuntimeException("dto is null!!");
        Optional<EmployeeInfoModel> optionalById = employeeInfoRepo.findById(empId);
        if (optionalById.isPresent()) {
            EmployeeInfoModel employeeInfoModel = optionalById.get();
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
        } else
            list.add("employee do not exist!!");
        return list;
    }

    //Converting EmpInfoModel to EmpInfoDto
    public EmployeeInfoDto convertToEmpInfoDto(EmployeeInfoModel employeeInfoModel) {
        return modelMapper.map(employeeInfoModel, EmployeeInfoDto.class);
    }
}
