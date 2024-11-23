package com.example.employeeservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "EmployeeDTO model information")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeDTO {
    @Schema(description = "Employee id")
    private Long id;
    @Schema(description = "Employee first name")
    private String firstName;
    @Schema(description = "Employee last name")
    private String lastName;
    @Schema(description = "Employee email")
    private String email;
    @Schema(description = "Employee department code")
    private String departmentCode;
    @Schema(description = "Employee organization code")
    private String organizationCode;
}
