package com.example.departmentservice.service.impl;

import com.example.departmentservice.DTO.DepartmentDTO;
import com.example.departmentservice.entity.Department;
import com.example.departmentservice.exception.DepartmentNotFoundException;
import com.example.departmentservice.exception.ResourceNotFoundException;
import com.example.departmentservice.repository.DepartmentRepository;
import com.example.departmentservice.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private DepartmentRepository departmentRepository;
    private ModelMapper modelMapper;

    @CachePut(value = "departments", key = "#result.departmentCode")
    @Override
    public DepartmentDTO saveDepartment(DepartmentDTO departmentDTO) {
        Department department = modelMapper.map(departmentDTO, Department.class);
        Department savedDepartment = departmentRepository.save(department);

        return modelMapper.map(savedDepartment, DepartmentDTO.class);
    }

    @Cacheable(value = "departments", key = "#departmentCode")
    @Override
    public DepartmentDTO getDepartment(String departmentCode) {
        Department department = departmentRepository.findByDepartmentCode(departmentCode)
                .orElseThrow(() -> new ResourceNotFoundException("department", "departmentCode", departmentCode));
        return modelMapper.map(department, DepartmentDTO.class);
    }

    @CachePut(value = "departments", key = "#departmentCode")
    @Transactional
    @Override
    public DepartmentDTO updateDepartment(String departmentCode, DepartmentDTO departmentDTO) {
        Department existingDepartment = departmentRepository.findByDepartmentCode(departmentCode)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with code " + departmentCode));

        modelMapper.typeMap(DepartmentDTO.class, Department.class)
                .addMappings(mapper ->
                        mapper.skip(Department::setDepartmentCode))
                .map(departmentDTO, existingDepartment);

        return modelMapper.map(
                departmentRepository.save(existingDepartment),
                DepartmentDTO.class
        );
    }


    @CachePut(value = "departments", key = "#departmentCode")
    @Transactional
    @Override
    public DepartmentDTO patchDepartment(String departmentCode, DepartmentDTO departmentDTO) {
        Department fetchedDept = departmentRepository.findByDepartmentCode(departmentCode)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with code " + departmentCode));

        if (departmentDTO.getDepartmentName() != null)
            fetchedDept.setDepartmentName(departmentDTO.getDepartmentName());
        if (departmentDTO.getDepartmentDescription() != null)
            fetchedDept.setDepartmentDescription(departmentDTO.getDepartmentDescription());

        return modelMapper.map(departmentRepository.save(fetchedDept), DepartmentDTO.class);
    }

    @CacheEvict(value = "departments", key = "#departmentCode")
    @Transactional
    @Override
    public void deleteDepartment(String departmentCode) {
        departmentRepository.findByDepartmentCode(departmentCode)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with code " + departmentCode));
        departmentRepository.deleteByDepartmentCode(departmentCode);
    }

}
