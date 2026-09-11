package com.ems.employee.service;

import com.ems.employee.entity.Department;
import com.ems.employee.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository){
        this.departmentRepository=departmentRepository;
    }

    public List<Department> getAllDepartments(){
        return departmentRepository.findAll();
    }

    public Department createDepartment(Department department){
        return departmentRepository.save(department);
    }

    public Optional<Department> getDepartmentById(int id){
        return departmentRepository.findById(id);
    }

    public Department updateDepartmentById(int id,Department department){
        Optional<Department> department1 = departmentRepository.findById(id);
        if(department1.isPresent()){
            Department existingDepartment = department1.get();
            existingDepartment.setName(department.getName());
            return departmentRepository.save(existingDepartment);
        }
        else {
            return null;
        }
    }

    public void deleteDepartmentById(int id){
        departmentRepository.deleteById(id);
        return;
    }

}
