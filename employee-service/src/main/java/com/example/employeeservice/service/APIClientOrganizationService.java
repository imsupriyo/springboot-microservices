package com.example.employeeservice.service;

import com.example.employeeservice.dto.OrganizationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ORGANIZATION-SERVICE")
public interface APIClientOrganizationService {

    @GetMapping("api/organizations/{organization-code}")
    OrganizationDTO getOrganization(@PathVariable("organization-code") String organizationCode);
}
