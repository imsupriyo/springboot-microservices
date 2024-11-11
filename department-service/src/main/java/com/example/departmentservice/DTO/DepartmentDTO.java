package com.example.departmentservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class DepartmentDTO {
    private Long id;
    private String departmentName;
    private String departmentDescription;
    private String departmentCode;
}
