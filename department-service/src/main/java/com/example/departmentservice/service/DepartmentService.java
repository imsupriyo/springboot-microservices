package com.example.departmentservice.service;

import com.example.departmentservice.DTO.DepartmentDTO;

public interface DepartmentService {
    DepartmentDTO saveDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO getDepartment(Long departmentId);
}
