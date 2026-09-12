package com.ems.employee.controller;

import com.ems.employee.dto.department.DepartmentRequestDTO;
import com.ems.employee.dto.department.DepartmentResponseDTO;
import com.ems.employee.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public List<DepartmentResponseDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @PostMapping("/departments")
    public DepartmentResponseDTO createDepartment(
            @Valid @RequestBody DepartmentRequestDTO requestDTO) {

        return departmentService.createDepartment(requestDTO);
    }

    @GetMapping("/departments/{id}")
    public DepartmentResponseDTO getDepartmentById(@PathVariable int id) {
        return departmentService.getDepartmentById(id);
    }

    @PutMapping("/departments/{id}")
    public DepartmentResponseDTO updateDepartmentById(
            @PathVariable int id,
            @Valid @RequestBody DepartmentRequestDTO requestDTO) {

        return departmentService.updateDepartmentById(id, requestDTO);
    }

    @DeleteMapping("/departments/{id}")
    public void deleteDepartmentById(@PathVariable int id) {
        departmentService.deleteDepartmentById(id);
    }
}