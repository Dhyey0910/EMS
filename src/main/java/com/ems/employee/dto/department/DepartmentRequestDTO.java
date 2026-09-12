package com.ems.employee.dto.department;

import jakarta.validation.constraints.NotBlank;

public class DepartmentRequestDTO {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}