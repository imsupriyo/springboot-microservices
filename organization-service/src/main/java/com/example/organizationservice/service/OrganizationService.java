package com.example.organizationservice.service;

import com.example.organizationservice.DTO.OrganizationDTO;

public interface OrganizationService {
    OrganizationDTO saveOrganization(OrganizationDTO organizationDTO);
    OrganizationDTO getOrganization(String organizationCode);
}
