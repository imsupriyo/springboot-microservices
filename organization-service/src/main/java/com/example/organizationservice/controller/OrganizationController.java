package com.example.organizationservice.controller;

import com.example.organizationservice.DTO.OrganizationDTO;
import com.example.organizationservice.service.OrganizationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/organizations")
@AllArgsConstructor
public class OrganizationController {
    private OrganizationService organizationService;

    @Operation(
            summary = "save organization REST API",
            description = "save organization REST API is used save an employee to database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<OrganizationDTO> saveOrganization(@RequestBody OrganizationDTO organizationDTO) {
        return new ResponseEntity<>(organizationService.saveOrganization(organizationDTO), HttpStatus.CREATED);
    }

    @Operation(
            summary = "get organization REST API",
            description = "get organization REST API is used fetch a department from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("{organization-code}")
    public ResponseEntity<OrganizationDTO> getOrganization(@PathVariable("organization-code") String organizationCode) {
        return ResponseEntity.ok(organizationService.getOrganization(organizationCode));
    }

}
