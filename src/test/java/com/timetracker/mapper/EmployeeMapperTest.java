package com.timetracker.mapper;

import com.timetracker.entity.Employee;
import com.timetracker.entity.History;
import com.timetracker.entity.Task;
import com.timetracker.enums.Status;
import com.timetracker.model.EmployeeDTO;
import com.timetracker.model.HistoryDTO;
import com.timetracker.model.TaskDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeMapperTest {

    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now();
        Long startTime = System.currentTimeMillis();
        Long endTime = System.currentTimeMillis();

        History history = History.builder()
                .id(1)
                .build();

        HistoryDTO historyDTO = HistoryDTO.builder()
                .id(1)
                .build();

        Task task = Task.builder()
                .id(1)
                .name("testTask")
                .startDate(startDate)
                .endDate(endDate)
                .status(Status.CREATED.name())
                .startTime(startTime)
                .endTime(endTime)
                .build();

        TaskDTO taskDTO = TaskDTO.builder()
                .id(1)
                .name("testTask")
                .startDate(startDate)
                .endDate(endDate)
                .status(Status.CREATED.name())
                .startTime(startTime)
                .endTime(endTime)
                .build();

        employee = Employee.builder()
                .id(1)
                .firstName("Test")
                .lastName("Testov")
                .history(history)
                .currentTask(task)
                .build();

        employeeDTO = EmployeeDTO.builder()
                .id(1)
                .firstName("Test")
                .lastName("Testov")
                .history(historyDTO)
                .currentTask(taskDTO)
                .build();
    }

    @Test
    void employeeToEmployeeDTO() {

        EmployeeDTO employeeDTO = EmployeeMapper.INSTANCE
                .employeeToEmployeeDTO(employee);
        assertThat(employee).usingRecursiveComparison()
                .isEqualTo(employeeDTO);
    }

    @Test
    void employeeDTOToEmployee() {

        Employee employee = EmployeeMapper.INSTANCE
                .employeeDTOToEmployee(employeeDTO);
        assertThat(employee).usingRecursiveComparison()
                .isEqualTo(employeeDTO);
    }
}