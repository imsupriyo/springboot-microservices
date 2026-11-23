package com.example.departmentservice.controller;

import com.example.departmentservice.DTO.DepartmentDTO;
import com.example.departmentservice.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(name = "Department Service Controller")
@RestController
@RequestMapping(value = "/api/departments")

@AllArgsConstructor
public class DepartmentController {
    private DepartmentService departmentService;

    @Operation(
            summary = "save department REST API",
            description = "save department REST API is used save an Department to database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<DepartmentDTO> saveDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return new ResponseEntity<>(departmentService.saveDepartment(departmentDTO), HttpStatus.CREATED);
    }

    @Operation(
            summary = "get department REST API",
            description = "get department REST API is used fetch a department from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("{departmentCode}")
    public ResponseEntity<DepartmentDTO> getDepartment(@PathVariable("departmentCode") String departmentCode) {
        return ResponseEntity.ok(departmentService.getDepartment(departmentCode));
    }


    @PutMapping("{departmentCode}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable String departmentCode, @RequestBody DepartmentDTO department) {
        return ResponseEntity.ok(departmentService.updateDepartment(departmentCode, department));
    }


    @PatchMapping("{departmentCode}")
    public ResponseEntity<DepartmentDTO> partialUpdateDepartment(@PathVariable String departmentCode, @RequestBody DepartmentDTO department) {
        return ResponseEntity.ok(departmentService.patchDepartment(departmentCode, department));
    }


    @DeleteMapping("{departmentCode}")
    public ResponseEntity<String> deleteDepartment(@PathVariable String departmentCode) {
        departmentService.deleteDepartment(departmentCode);
        return ResponseEntity.ok(departmentCode + " deleted");
    }
}
