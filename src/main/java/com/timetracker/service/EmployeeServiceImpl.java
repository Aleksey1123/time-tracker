package com.timetracker.service;

import com.timetracker.entity.Employee;
import com.timetracker.entity.Task;
import com.timetracker.enums.Status;
import com.timetracker.exception.EmployeeAlreadyExistsException;
import com.timetracker.exception.EmployeeNotFoundException;
import com.timetracker.exception.TaskAlreadyExistsException;
import com.timetracker.exception.TaskNotFoundException;
import com.timetracker.mapper.EmployeeMapper;
import com.timetracker.model.EmployeeDTO;
import com.timetracker.repository.EmployeeRepository;
import com.timetracker.sorting.PageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PageBuilder pageBuilder;
    private final EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;
    private static final String EMPLOYEE_NOT_FOUND_EXC_MESSAGE = "employee not found";
    private static final String EMPLOYEE_ALREADY_EXISTS_EXC_MESSAGE = "employee already exists, provide a different name";

    @Override
    public EmployeeDTO getEmployeeById(Integer id) {

        Optional<Employee> optionalTask = employeeRepository.findById(id);
        if (optionalTask.isEmpty())
            throw new TaskNotFoundException(EMPLOYEE_NOT_FOUND_EXC_MESSAGE);
        return optionalTask.map(employeeMapper::employeeToEmployeeDTO).get();
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

        Optional<Employee> employeeOptional = employeeRepository.findById(employeeId);
        if (employeeOptional.isEmpty())
            throw new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND_EXC_MESSAGE);
        employeeRepository.deleteById(employeeId);
        return employeeOptional.get();
    }

    @Override
    public Employee updateEmployeeById(Integer taskId, EmployeeDTO employeeDTO) {

        Employee foundEmployee = employeeRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        foundEmployee.setFirstName(employeeDTO.getFirstName());
        foundEmployee.setLastName(employeeDTO.getLastName());
        return foundEmployee;
    }
}
