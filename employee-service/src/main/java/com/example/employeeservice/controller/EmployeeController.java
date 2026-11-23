package com.example.employeeservice.controller;

import com.example.employeeservice.dto.APIResponseDTO;
import com.example.employeeservice.dto.EmployeeDTO;
import com.example.employeeservice.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Employee Service Controller")
@RestController
@RequestMapping("/api/employees")
@AllArgsConstructor
public class EmployeeController {
    private EmployeeService employeeService;

    @Operation(
            summary = "save employee REST API",
            description = "save employee REST API is used save an employee to database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @PostMapping
    public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return new ResponseEntity<>(employeeService.saveEmployee(employeeDTO), HttpStatus.CREATED);
    }


    @Operation(
            summary = "get employee REST API",
            description = "get employee REST API is used fetch an employee from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("{employeeId}")
    public ResponseEntity<APIResponseDTO> getEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }


    @PutMapping("{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeDTO employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(employeeId, employee));
    }


    @PatchMapping("{employeeId}")
    public ResponseEntity<EmployeeDTO> partialUpdateEmployee(@PathVariable Long employeeId, @RequestBody EmployeeDTO employee) {
        return ResponseEntity.ok(employeeService.patchEmployee(employeeId, employee));
    }


    @DeleteMapping("{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.ok("Employee id" + employeeId + " deleted");
    }
}
