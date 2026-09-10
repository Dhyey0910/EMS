package com.ems.employee.service;

import com.ems.employee.entity.Employee;
import com.ems.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    public Employee createEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployeeById(int id){
        return employeeRepository.findById(id);
    }

    public Employee updateEmployee(int id,Employee employee){
        Optional<Employee> employee1 =  employeeRepository.findById(id);
        if (employee1.isPresent()) {
            Employee existingEmployee = employee1.get();

            existingEmployee.setName(employee.getName());
            existingEmployee.setSalary(employee.getSalary());
            existingEmployee.setRole(employee.getRole());
            existingEmployee.setJoiningDate(employee.getJoiningDate());
            existingEmployee.setPhoneNumber(employee.getPhoneNumber());
            existingEmployee.setEmail(employee.getEmail());
            existingEmployee.setPassword(employee.getPassword());

            return employeeRepository.save(existingEmployee);
        }
        else{
            return null;
        }
    }

    public void deleteEmployeeById( int id){
        employeeRepository.deleteById(id);
        return;
    }
}
