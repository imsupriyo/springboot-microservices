package com.example.employeeservice.service.impl;

import com.example.employeeservice.dto.APIResponseDTO;
import com.example.employeeservice.dto.DepartmentDTO;
import com.example.employeeservice.dto.EmployeeDTO;
import com.example.employeeservice.dto.OrganizationDTO;
import com.example.employeeservice.entity.Employee;
import com.example.employeeservice.exception.ResourceNotFoundException;
import com.example.employeeservice.repository.EmployeeRepository;
import com.example.employeeservice.service.APIClient;
import com.example.employeeservice.service.APIClientOrganizationService;
import com.example.employeeservice.service.EmployeeService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private EmployeeRepository employeeRepository;
    private ModelMapper modelMapper;
    //    private RestTemplate restTemplate;
//    private WebClient webClient;
    private APIClient apiClient;
    private APIClientOrganizationService clientOrganizationService;


    @Override
    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = modelMapper.map(employeeDTO, Employee.class);

        Employee savedEmployee = employeeRepository.save(employee);
        return modelMapper.map(savedEmployee, EmployeeDTO.class);
    }

    //    @CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getDefaultEmployee")
    @Retry(name = "${spring.application.name}", fallbackMethod = "getDefaultEmployee")
    @Override
    public APIResponseDTO getEmployeeById(Long employeeId) {
        LOGGER.info("inside getEmployeeById() method");

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("employee", "id", employeeId));

//        ResponseEntity<DepartmentDTO> responseEntity = restTemplate.getForEntity("http://localhost:8080/api/departments/" + employee.getDepartmentCode(), DepartmentDTO.class);
//        DepartmentDTO departmentDTO = responseEntity.getBody();

//        DepartmentDTO departmentDTO = webClient.get()
//                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
//                .retrieve()
//                .bodyToMono(DepartmentDTO.class)
//                .block();

        DepartmentDTO departmentDTO = apiClient.getDepartment(employee.getDepartmentCode());
        OrganizationDTO organizationDTO = clientOrganizationService.getOrganization(employee.getOrganizationCode());
        return APIResponseDTO.builder()
                .employee(modelMapper.map(employee, EmployeeDTO.class))
                .department(departmentDTO)
                .organization(organizationDTO)
                .build();
    }

    public APIResponseDTO getDefaultEmployee(Long employeeId, Exception e) {
        LOGGER.info("inside getDefaultEmployee() method");

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("employee", "id", employeeId));

        DepartmentDTO departmentDTO = new DepartmentDTO
                (
                        2L,
                        "R&D",
                        "Research and Development",
                        "RD101"
                );

        OrganizationDTO organizationDTO = new OrganizationDTO
                (
                        2L,
                        "ABC Organization",
                        "ABC Organization Description",
                        "ABC101",
                        LocalDateTime.now()
                );

        return APIResponseDTO.builder()
                .employee(modelMapper.map(employee, EmployeeDTO.class))
                .department(departmentDTO)
                .organization(organizationDTO)
                .build();
    }
}
