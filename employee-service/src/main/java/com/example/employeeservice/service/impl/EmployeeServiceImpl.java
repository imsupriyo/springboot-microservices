package com.example.employeeservice.service.impl;

import com.example.employeeservice.dto.APIResponseDTO;
import com.example.employeeservice.dto.EmployeeDTO;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.exception.EmployeeNotFoundException;
import com.example.employeeservice.exception.ResourceNotFoundException;
import com.example.employeeservice.repository.EmployeeRepository;
import com.example.employeeservice.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;
    //    private RestTemplate restTemplate;
//    private WebClient webClient;
    private RetryService retryService;

    @CachePut(value = "employees", key = "#result.id")
    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        return modelMapper.map(
                employeeRepository.save(modelMapper.typeMap(EmployeeDTO.class, Employee.class)
                        .addMappings(mapper -> mapper.skip(Employee::setId)).map(employeeDTO)),
                EmployeeDTO.class);
    }

    @Cacheable(value = "employeeDetails", key = "#employeeId")
    @Override
    public APIResponseDTO getEmployeeById(Long employeeId) {
        log.info("inside getEmployeeById() method");

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("employee", "id", employeeId));

//        ResponseEntity<DepartmentDTO> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/" + employee.getDepartmentCode(), DepartmentDTO.class);
//        DepartmentDTO departmentDTO = responseEntity.getBody();

//        DepartmentDTO departmentDTO = webClient.get()
//                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
//                .retrieve()
//                .bodyToMono(DepartmentDTO.class)
//                .block();

        return APIResponseDTO.builder()
                .employee(modelMapper.map(employee, EmployeeDTO.class))
                .department(retryService.getDepartment(employee.getDepartmentCode()))
                .organization(retryService.getOrganization(employee.getOrganizationCode()))
                .build();
    }

    @Caching(
            put = {
                    @CachePut(value = "employees", key = "#employeeId")
            },
            evict = {
                    @CacheEvict(value = "employeeDetails", key = "#employeeId")
            }
    )
    @Transactional
    @Override
    public EmployeeDTO updateEmployee(Long employeeId, EmployeeDTO employee) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found with id " + employeeId));

        modelMapper.typeMap(EmployeeDTO.class, Employee.class)
                .addMappings(mapper -> mapper.skip(Employee::setId))
                .map(employee, existingEmployee);

        Employee updatedEmployee = employeeRepository.save(existingEmployee);

        return modelMapper.map(updatedEmployee, EmployeeDTO.class);
    }

    @CacheEvict(value = "employees", key = "#employeeId")
    @Transactional
    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id " + employeeId));
        employeeRepository.deleteById(employeeId);
    }

    @CachePut(value = "employees", key = "#employeeId")
    @Transactional
    @Override
    public EmployeeDTO patchEmployee(Long employeeId, EmployeeDTO employee) {

        Employee fetchedEmp = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id " + employeeId));

        if (employee.getEmail() != null)
            fetchedEmp.setEmail(employee.getEmail());
        if (employee.getFirstName() != null)
            fetchedEmp.setFirstName(employee.getFirstName());
        if (employee.getLastName() != null)
            fetchedEmp.setLastName(employee.getLastName());
        if (employee.getDepartmentCode() != null)
            fetchedEmp.setDepartmentCode(employee.getDepartmentCode());
        if (employee.getOrganizationCode() != null)
            fetchedEmp.setOrganizationCode(employee.getOrganizationCode());

        return modelMapper.map(employeeRepository.save(fetchedEmp), EmployeeDTO.class);
    }
}
