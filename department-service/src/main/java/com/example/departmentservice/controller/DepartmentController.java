package com.example.departmentservice.controller;

import com.example.departmentservice.DTO.DepartmentDTO;
import com.example.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/departments")

@AllArgsConstructor
public class DepartmentController {
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<DepartmentDTO> saveDepartment(@RequestBody DepartmentDTO departmentDTO) {
        return new ResponseEntity<>(departmentService.saveDepartment(departmentDTO), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<DepartmentDTO> saveDepartment(@PathVariable("id") Long departmentId) {
        return ResponseEntity.ok(departmentService.getDepartment(departmentId));
    }
}
