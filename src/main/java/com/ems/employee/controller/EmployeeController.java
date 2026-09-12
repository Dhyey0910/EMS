package com.ems.employee.controller;

import com.ems.employee.dto.employee.EmployeeRequestDTO;
import com.ems.employee.dto.employee.EmployeeResponseDTO;
import com.ems.employee.entity.Employee;
import com.ems.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeService.getAllEmployeeDTOs();
    }

    @PostMapping("/employees")
    public EmployeeResponseDTO createEmployee(
            @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {

        return employeeService.createEmployee(employeeRequestDTO);
    }

    @GetMapping("/employees/{id}")
    public EmployeeResponseDTO getEmployeeById(@PathVariable int id) {
        Employee employee = employeeService.getEmployeeById(id);
        return employeeService.convertToDTO(employee);
    }

    @PutMapping("/employees/{id}")
    public EmployeeResponseDTO updateEmployeeById(
            @PathVariable int id,
            @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {

        return employeeService.updateEmployee(id, employeeRequestDTO);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployeeById(@PathVariable int id){
        employeeService.deleteEmployeeById(id);
        return;
    }

    @PutMapping("/employees/{employeeId}/department/{departmentId}")
    public Employee assignDepartment(@PathVariable int employeeId, @PathVariable int departmentId){
        return employeeService.assignDepartment(employeeId,departmentId);
    }

    @PutMapping("/employees/{employeeId}/manager/{managerId}")
    public Employee assignManager(@PathVariable int employeeId, @PathVariable int managerId){
        return employeeService.assignManager(employeeId,managerId);
    }
}

