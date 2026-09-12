package com.ems.employee.service;

import com.ems.employee.dto.employee.EmployeeRequestDTO;
import com.ems.employee.dto.employee.EmployeeResponseDTO;
import com.ems.employee.entity.Department;
import com.ems.employee.entity.Employee;
import com.ems.employee.exception.ResourceNotFoundException;
import com.ems.employee.repository.DepartmentRepository;
import com.ems.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    private DepartmentRepository departmentRepository;

    EmployeeService(EmployeeRepository employeeRepository,DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository=departmentRepository;
    }
    
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {

        Employee employee = new Employee();

        employee.setName(employeeRequestDTO.getName());
        employee.setSalary(employeeRequestDTO.getSalary());
        employee.setRole(employeeRequestDTO.getRole());
        employee.setJoiningDate(employeeRequestDTO.getJoiningDate());
        employee.setPhoneNumber(employeeRequestDTO.getPhoneNumber());
        employee.setEmail(employeeRequestDTO.getEmail());
        employee.setPassword(employeeRequestDTO.getPassword());

        Employee savedEmployee = employeeRepository.save(employee);

        return convertToDTO(savedEmployee);
    }

    public Employee getEmployeeById(int id){
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
    }

    public EmployeeResponseDTO updateEmployee(int id, EmployeeRequestDTO employeeRequestDTO) {

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        existingEmployee.setName(employeeRequestDTO.getName());
        existingEmployee.setSalary(employeeRequestDTO.getSalary());
        existingEmployee.setRole(employeeRequestDTO.getRole());
        existingEmployee.setJoiningDate(employeeRequestDTO.getJoiningDate());
        existingEmployee.setPhoneNumber(employeeRequestDTO.getPhoneNumber());
        existingEmployee.setEmail(employeeRequestDTO.getEmail());
        existingEmployee.setPassword(employeeRequestDTO.getPassword());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        return convertToDTO(updatedEmployee);
    }

    public void deleteEmployeeById(int id) {

        employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        employeeRepository.deleteById(id);
    }

    public Employee assignDepartment(int employeeId, int departmentId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }

    public Employee assignManager(int employeeId, int managerId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        Employee manager = employeeRepository.findById(managerId)
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));

        employee.setManager(manager);
        return employeeRepository.save(employee);
    }

    public EmployeeResponseDTO convertToDTO(Employee employee) {

        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setSalary(employee.getSalary());
        dto.setRole(employee.getRole());
        dto.setJoiningDate(employee.getJoiningDate());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setEmail(employee.getEmail());

        return dto;
    }

    public List<EmployeeResponseDTO> getAllEmployeeDTOs() {

        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeResponseDTO> dtos = new ArrayList<>();
        for (Employee employee : employees) {
            dtos.add(convertToDTO(employee));
        }
        return dtos;
    }
}
