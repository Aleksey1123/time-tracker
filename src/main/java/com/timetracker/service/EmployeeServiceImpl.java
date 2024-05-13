package com.timetracker.service;

import com.timetracker.entity.Employee;
import com.timetracker.enums.EntityNames;
import com.timetracker.exception.EmployeeAlreadyExistsException;
import com.timetracker.exception.EmployeeNotFoundException;
import com.timetracker.exception.TaskAlreadyExistsException;
import com.timetracker.exception.TaskNotFoundException;
import com.timetracker.mapper.EmployeeMapper;
import com.timetracker.model.EmployeeDTO;
import com.timetracker.repository.EmployeeRepository;
import com.timetracker.sorting.PageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PageBuilder pageBuilder;
    private final EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;
    private static final String EMPLOYEE_NOT_FOUND_EXC_MESSAGE = "employee not found";
    private static final String EMPLOYEE_ALREADY_EXISTS_EXC_MESSAGE = "employee already exists, provide a different name";

    @Override
    public Page<EmployeeDTO> getAllEmployees(Integer pageNumber, Integer pageSize,
                                     String sortingParam) {
        PageRequest pageRequest = pageBuilder.buildPageRequest(pageNumber, pageSize,
                sortingParam, EntityNames.Employee.name());

        return new PageImpl<>(employeeRepository.findAll(pageRequest)
                .stream()
                .map(employeeMapper::employeeToEmployeeDTO)
                .collect(Collectors.toList()));
    }

    @Override
    public EmployeeDTO getEmployeeById(Integer employeeId) {

        Employee foundEmployee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND_EXC_MESSAGE));

        return employeeMapper.employeeToEmployeeDTO(foundEmployee);
    }

    @Override
    public Employee saveNewEmployee(EmployeeDTO employeeDTO) {

        if (employeeRepository.findByFirstNameAndLastName(employeeDTO.getFirstName(), employeeDTO.getLastName()).isPresent())
            throw new EmployeeAlreadyExistsException(EMPLOYEE_ALREADY_EXISTS_EXC_MESSAGE);

        Employee employee = employeeMapper.employeeDTOToEmployee(employeeDTO);
        employee.setFirstName(employeeDTO.getFirstName());
        employee.setLastName(employee.getLastName());

        return employeeRepository.save(employee);
    }

    @Override
    public Employee deleteEmployeeById(Integer employeeId) {

        Employee foundEmployee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND_EXC_MESSAGE));
        employeeRepository.deleteById(employeeId);

        return foundEmployee;
    }

    @Override
    public Employee updateEmployeeById(Integer employeeId, EmployeeDTO employeeDTO) {

        Employee foundEmployee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND_EXC_MESSAGE));
        foundEmployee.setFirstName(employeeDTO.getFirstName());
        foundEmployee.setLastName(employeeDTO.getLastName());

        return foundEmployee;
    }


}
