package com.example.organizationservice.service.impl;

import com.example.organizationservice.DTO.OrganizationDTO;
import com.example.organizationservice.entity.Organization;
import com.example.organizationservice.exception.OrganizationNotFoundException;
import com.example.organizationservice.exception.ResourceNotFoundException;
import com.example.organizationservice.repository.OrganizationRepository;
import com.example.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private OrganizationRepository organizationRepository;
    private ModelMapper modelMapper;

    @CachePut(value = "organizations", key = "#result.organizationCode")
    @Override
    public OrganizationDTO saveOrganization(OrganizationDTO organizationDTO) {
        Organization organization = dtoToEntity(organizationDTO);
        Organization savedOrganization = organizationRepository.save(organization);

        return entityToDTO(savedOrganization);
    }

    @Cacheable(value = "organizations", key = "#organizationCode")
    @Override
    public OrganizationDTO getOrganization(String organizationCode) {
        Organization organization = organizationRepository.findByOrganizationCode(organizationCode)
                .orElseThrow(() -> new ResourceNotFoundException("organization", "organizationCode", organizationCode));

        return entityToDTO(organization);
    }


    @CachePut(value = "organizations", key = "#organizationCode")
    @Transactional
    @Override
    public OrganizationDTO updateOrganization(String organizationCode, OrganizationDTO organizationDTO) {
        Organization existingOrganization = organizationRepository.findByOrganizationCode(organizationCode)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization not found with code " + organizationCode));

        modelMapper.map(organizationDTO, existingOrganization);

        return entityToDTO(organizationRepository.save(existingOrganization));
    }


    @CachePut(value = "organizations", key = "#organizationCode")
    @Transactional
    @Override
    public OrganizationDTO patchOrganization(String organizationCode, OrganizationDTO organizationDTO) {
        Organization fetchedDept = organizationRepository.findByOrganizationCode(organizationCode)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization not found with code " + organizationCode));

        if (organizationDTO.getOrganizationName() != null)
            fetchedDept.setOrganizationName(organizationDTO.getOrganizationName());
        if (organizationDTO.getOrganizationDescription() != null)
            fetchedDept.setOrganizationDescription(organizationDTO.getOrganizationDescription());

        return entityToDTO(organizationRepository.save(fetchedDept));
    }

    @CacheEvict(value = "organizations", key = "#organizationCode")
    @Transactional
    @Override
    public void deleteOrganization(String organizationCode) {
        organizationRepository.findByOrganizationCode(organizationCode)
                .orElseThrow(() -> new OrganizationNotFoundException("Organization not found with code " + organizationCode));
        organizationRepository.deleteByOrganizationCode(organizationCode);
    }

    private OrganizationDTO entityToDTO(Organization organization) {
        return modelMapper.map(organization, OrganizationDTO.class);
    }

    private Organization dtoToEntity(OrganizationDTO organizationDTO) {
        return modelMapper.map(organizationDTO, Organization.class);
    }
}

