package com.example.employeeservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "APIResponseDTO model information")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class APIResponseDTO {
    @Schema(description = "holds EmployeeDTO object")
    private EmployeeDTO employee;
    @Schema(description = "holds DepartmentDTO object")
    private DepartmentDTO department;
    @Schema(description = "holds OrganizationDTO object")
    private OrganizationDTO organization;
}
