package com.example.employeeservice.service;

import com.example.employeeservice.dto.APIResponseDTO;
import com.example.employeeservice.dto.EmployeeDTO;

public interface EmployeeService {
    EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);

    APIResponseDTO getEmployeeById(Long employeeId);

    EmployeeDTO updateEmployee(Long employeeId, EmployeeDTO employee);

    EmployeeDTO patchEmployee(Long employeeId, EmployeeDTO employee);

    void deleteEmployee(Long employeeId);

}
