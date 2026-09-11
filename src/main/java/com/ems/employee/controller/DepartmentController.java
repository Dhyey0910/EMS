package com.ems.employee.controller;

import com.ems.employee.entity.Department;
import com.ems.employee.repository.DepartmentRepository;
import com.ems.employee.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService){
        this.departmentService=departmentService;
    }

    @GetMapping("/departments")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @PostMapping("/departments")
    public Department createDepartment(@RequestBody Department department){
        return departmentService.createDepartment(department);
    }

    @GetMapping("/departments/{id}")
    public Optional<Department> getDepartmentById(@PathVariable int id){
        return departmentService.getDepartmentById(id);
    }

    @PutMapping("/departments/{id}")
    public Department updateDepartmentById(@PathVariable int id, @RequestBody Department department){
        return departmentService.updateDepartmentById(id,department);
    }

    @DeleteMapping("/departments/{id}")
    public void deleteDepartmentById(@PathVariable int id){
        departmentService.deleteDepartmentById(id);
        return;
    }
}
