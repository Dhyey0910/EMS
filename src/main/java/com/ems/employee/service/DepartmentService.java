package com.ems.employee.service;

import com.ems.employee.dto.department.DepartmentRequestDTO;
import com.ems.employee.dto.department.DepartmentResponseDTO;
import com.ems.employee.entity.Department;
import com.ems.employee.exception.ResourceNotFoundException;
import com.ems.employee.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO requestDTO) {

        Department department = new Department();

        department.setName(requestDTO.getName());

        Department savedDepartment = departmentRepository.save(department);

        return convertToDTO(savedDepartment);
    }

    public List<DepartmentResponseDTO> getAllDepartments() {

        List<Department> departments = departmentRepository.findAll();

        List<DepartmentResponseDTO> dtos = new ArrayList<>();

        for (Department department : departments) {
            dtos.add(convertToDTO(department));
        }

        return dtos;
    }

    public DepartmentResponseDTO getDepartmentById(int id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        return convertToDTO(department);
    }

    public DepartmentResponseDTO updateDepartmentById(
            int id,
            DepartmentRequestDTO requestDTO) {

        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        existingDepartment.setName(requestDTO.getName());

        Department updatedDepartment = departmentRepository.save(existingDepartment);

        return convertToDTO(updatedDepartment);
    }

    public void deleteDepartmentById(int id) {

        departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        departmentRepository.deleteById(id);
    }

    public DepartmentResponseDTO convertToDTO(Department department) {

        DepartmentResponseDTO dto = new DepartmentResponseDTO();

        dto.setId(department.getId());
        dto.setName(department.getName());

        return dto;
    }
}