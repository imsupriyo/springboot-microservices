package com.example.organizationservice.service;

import com.example.organizationservice.DTO.OrganizationDTO;

public interface OrganizationService {
    OrganizationDTO saveOrganization(OrganizationDTO organizationDTO);

    OrganizationDTO getOrganization(String organizationCode);

    OrganizationDTO updateOrganization(String organizationCode, OrganizationDTO organization);

    OrganizationDTO patchOrganization(String organizationCode, OrganizationDTO organization);

    void deleteOrganization(String organizationCode);
}
