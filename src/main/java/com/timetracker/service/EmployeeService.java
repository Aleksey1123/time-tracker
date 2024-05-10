package com.timetracker.service;

import com.timetracker.entity.Employee;
import com.timetracker.entity.Task;
import com.timetracker.model.EmployeeDTO;
import com.timetracker.model.TaskDTO;

public interface EmployeeService {

    EmployeeDTO getEmployeeById(Integer id);

    Employee saveNewEmployee(EmployeeDTO employeeDTO);

    Employee deleteEmployeeById(Integer employeeId);

    Employee updateEmployeeById(Integer taskId, EmployeeDTO employeeDTO);
}
