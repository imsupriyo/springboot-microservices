package com.example.employeeservice.service.impl;

import com.example.employeeservice.dto.DepartmentDTO;
import com.example.employeeservice.dto.OrganizationDTO;
import com.example.employeeservice.service.APIClient;
import com.example.employeeservice.service.APIClientOrganizationService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RetryService {
    private final static Logger LOGGER = LoggerFactory.getLogger(RetryService.class);
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
        LOGGER.info("inside getDefaultDepartment() method");

        return new DepartmentDTO
                (
                        2L,
                        "R&D",
                        "Research and Development",
                        "RD101"
                );
    }

    public OrganizationDTO getDefaultOrganization(String organizationCode, Exception e) {
        LOGGER.info("inside getDefaultOrganization() method");

        return new OrganizationDTO
                (
                        2L,
                        "ABC Organization",
                        "ABC Organization Description",
                        "ABC101",
                        LocalDateTime.now()
                );
    }
}
