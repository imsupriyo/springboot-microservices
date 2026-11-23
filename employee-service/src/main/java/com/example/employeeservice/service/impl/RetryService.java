package com.example.employeeservice.service.impl;

import com.example.employeeservice.dto.DepartmentDTO;
import com.example.employeeservice.dto.OrganizationDTO;
import com.example.employeeservice.service.APIClient;
import com.example.employeeservice.service.APIClientOrganizationService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
public class RetryService {
    private APIClient apiClient;
    private APIClientOrganizationService clientOrganizationService;

    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultOrganization")
    public OrganizationDTO getOrganization(String organizationCode) {
        return clientOrganizationService.getOrganization(organizationCode);
    }

    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultDepartment")
    public DepartmentDTO getDepartment(String departmentCode) {
        return apiClient.getDepartment(departmentCode);
    }

    public DepartmentDTO getDefaultDepartment(String departmentCode, Exception e) {
        log.info("inside getDefaultDepartment() method");

        return new DepartmentDTO
                (
                        "R&D",
                        "Research and Development",
                        "RD101"
                );
    }

    public OrganizationDTO getDefaultOrganization(String organizationCode, Exception e) {
        log.info("inside getDefaultOrganization() method");

        return new OrganizationDTO
                (
                        "ABC Organization",
                        "ABC Organization Description",
                        "ABC101",
                        LocalDateTime.now()
                );
    }
}
