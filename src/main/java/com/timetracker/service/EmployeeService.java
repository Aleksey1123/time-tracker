package com.timetracker.service;

import com.timetracker.entity.Employee;
import com.timetracker.model.EmployeeDTO;
import org.springframework.data.domain.Page;

public interface EmployeeService {

    Page<EmployeeDTO> getAllEmployees(Integer pageNumber, Integer pageSize,
                                      String sortingParam);

    EmployeeDTO getEmployeeById(Integer id);

    Employee saveNewEmployee(EmployeeDTO employeeDTO);

    Employee deleteEmployeeById(Integer employeeId);

    Employee updateEmployeeById(Integer taskId, EmployeeDTO employeeDTO);
}
