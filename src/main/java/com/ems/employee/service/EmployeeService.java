package com.ems.employee.service;

import com.ems.employee.entity.Department;
import com.ems.employee.entity.Employee;
import com.ems.employee.repository.DepartmentRepository;
import com.ems.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

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

    public Employee assignDepartment(int employeeId, int departmentId){
        Optional<Employee> employee1 = employeeRepository.findById(employeeId);
        Optional<Department> department1 = departmentRepository.findById(departmentId);

        if(employee1.isPresent() && department1.isPresent()){
            Employee employee = employee1.get();
            Department department = department1.get();

            employee.setDepartment(department);

            return employeeRepository.save(employee);
        }
        else{
            return null;
        }
    }
}
