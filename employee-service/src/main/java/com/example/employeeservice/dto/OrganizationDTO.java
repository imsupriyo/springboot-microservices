package com.example.employeeservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "OrganizationDTO model information")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrganizationDTO {
    @Schema(description = "Organization id")
    private Long id;
    @Schema(description = "Organization name")
    private String organizationName;
    @Schema(description = "Organization description")
    private String organizationDescription;
    @Schema(description = "Organization code")
    private String organizationCode;
    @Schema(description = "Organization created date")
    private LocalDateTime createdDate;
}
