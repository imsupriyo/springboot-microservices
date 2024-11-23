package com.example.organizationservice.service.impl;

import com.example.organizationservice.DTO.OrganizationDTO;
import com.example.organizationservice.entity.Organization;
import com.example.organizationservice.exception.ResourceNotFoundException;
import com.example.organizationservice.repository.OrganizationRepository;
import com.example.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private OrganizationRepository organizationRepository;
    private ModelMapper modelMapper;

    @Override
    public OrganizationDTO saveOrganization(OrganizationDTO organizationDTO) {
        Organization organization = DTOtoEntity(organizationDTO);
        Organization savedOrganization = organizationRepository.save(organization);

        return entityToDTO(savedOrganization);
    }

    @Override
    public OrganizationDTO getOrganization(String organizationCode) {
        Organization organization = organizationRepository.findByOrganizationCode(organizationCode)
                .orElseThrow(() -> new ResourceNotFoundException("organization", "organizationCode", organizationCode));

        return entityToDTO(organization);
    }

    private OrganizationDTO entityToDTO(Organization organization) {
        return modelMapper.map(organization, OrganizationDTO.class);
    }

    private Organization DTOtoEntity(OrganizationDTO organizationDTO) {
        return modelMapper.map(organizationDTO, Organization.class);
    }
}

