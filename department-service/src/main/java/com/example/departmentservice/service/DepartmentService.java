package com.example.departmentservice.service;

import com.example.departmentservice.DTO.DepartmentDTO;

public interface DepartmentService {
    DepartmentDTO saveDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO getDepartment(String departmentCode);

    DepartmentDTO updateDepartment(String departmentCode, DepartmentDTO departmentDTO);

    DepartmentDTO patchDepartment(String departmentCode, DepartmentDTO departmentDTO);

    void deleteDepartment(String departmentCode);
}
