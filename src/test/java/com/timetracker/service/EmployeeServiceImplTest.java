package com.timetracker.service;

import com.timetracker.entity.Employee;
import com.timetracker.exception.EmployeeAlreadyExistsException;
import com.timetracker.exception.EmployeeNotFoundException;
import com.timetracker.model.EmployeeDTO;
import com.timetracker.repository.EmployeeRepository;
import com.timetracker.sorting.PageBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    EmployeeRepository employeeRepository;
    @Mock
    PageBuilder pageBuilder;
    @InjectMocks
    EmployeeServiceImpl employeeService;

    private Employee testEmployee;
    private EmployeeDTO testDTOEmployee;

    @BeforeEach
    void setUp() {

        testEmployee = Employee.builder()
                .id(100)
                .firstName("test")
                .lastName("Task")
                .build();

        testDTOEmployee = EmployeeDTO.builder()
                .id(100)
                .firstName("test")
                .lastName("Task")
                .build();
    }

    @Test
    void getAllEmployees() {

        Page<Employee> employees = new PageImpl<>(List.of(Employee.builder().build()));

        given(pageBuilder.buildPageRequest(any(), any(), any(), any()))
                .willReturn(PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "id")));

        given(employeeRepository.findAll((Pageable) any())).willReturn(employees);

        Page<EmployeeDTO> dtoEmployees = employeeService.getAllEmployees(0, 0, "id");

        assertEquals(1, dtoEmployees.getSize());
    }

    @Test
    void getEmployeeById() {

        given(employeeRepository.findById(1)).willReturn(Optional.of(testEmployee));
        EmployeeDTO actualEmployeeDTO = employeeService.getEmployeeById(1);

        assertEquals(testEmployee.getFirstName(), actualEmployeeDTO.getFirstName());
        assertEquals(testEmployee.getLastName(), actualEmployeeDTO.getLastName());
    }

    @Test
    void getEmployeeByIdNotFound() {

        given(employeeRepository.findById(1)).willReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.getEmployeeById(1));
    }

    @Test
    void saveNewEmployee() {

        given(employeeRepository.findByFirstNameAndLastName(any(), any()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(any())).willReturn(testEmployee);

        Employee savedEmployee = employeeService
                .saveNewEmployee(testDTOEmployee);
        assertThat(savedEmployee).isNotNull();
    }

    @Test
    void saveNewEmployeeAlreadyExists() {

        given(employeeRepository.findByFirstNameAndLastName(any(), any()))
                .willReturn(Optional.ofNullable(testEmployee));

        assertThrows(EmployeeAlreadyExistsException.class,
                () -> employeeService.saveNewEmployee(testDTOEmployee));
    }

    @Test
    void deleteEmployeeByIdNotFound() {

        given(employeeRepository.findById(1)).willReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.deleteEmployeeById(1));
    }

    @Test
    void deleteEmployeeById() {

        given(employeeRepository.findById(1)).willReturn(Optional.of(testEmployee));

        Employee deletedEmployee = employeeService.deleteEmployeeById(1);
        assertThat(deletedEmployee).isNotNull();
    }

    @Test
    void updateEmployeeByIdNotFound() {

        given(employeeRepository.findById(1)).willReturn(Optional.empty());

        assertThrows(EmployeeNotFoundException.class,
                () -> employeeService.updateEmployeeById(1, any()));
    }

    @Test
    void updateEmployeeById() {

        given(employeeRepository.findById(1)).willReturn(Optional.of(testEmployee));

        Employee updatedEmployee = employeeService.updateEmployeeById(1, testDTOEmployee);
        assertEquals(testDTOEmployee.getFirstName(), updatedEmployee.getFirstName());
        assertEquals(testDTOEmployee.getLastName(), updatedEmployee.getLastName());
    }
}