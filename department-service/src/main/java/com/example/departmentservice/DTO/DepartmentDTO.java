package com.example.departmentservice.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "DepartmentDTO model information")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DepartmentDTO {
    @Schema(description = "Department id")
    private Long id;
    @Schema(description = "Department name")
    private String departmentName;
    @Schema(description = "Department description")
    private String departmentDescription;
    @Schema(description = "Department code")
    private String departmentCode;
}
