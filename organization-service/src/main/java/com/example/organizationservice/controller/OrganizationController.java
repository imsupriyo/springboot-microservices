package com.example.organizationservice.controller;

import com.example.organizationservice.DTO.OrganizationDTO;
import com.example.organizationservice.entity.Organization;
import com.example.organizationservice.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/organizations")
@AllArgsConstructor
public class OrganizationController {
    private OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<OrganizationDTO> saveOrganization(@RequestBody OrganizationDTO organizationDTO) {
        return new ResponseEntity<>(organizationService.saveOrganization(organizationDTO), HttpStatus.CREATED);
    }

    @GetMapping("{organization-code}")
    public ResponseEntity<OrganizationDTO> getOrganization(@PathVariable("organization-code") String organizationCode) {
        return ResponseEntity.ok(organizationService.getOrganization(organizationCode));
    }

}
