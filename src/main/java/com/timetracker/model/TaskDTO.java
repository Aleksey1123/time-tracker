package com.timetracker.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class TaskDTO {

    private Integer id;
    private String name;

    private Long startTime;
    private LocalDateTime startDate;
    private Long endTime;
    private LocalDateTime endDate;
    private String status;

    private List<EmployeeDTO> assignedEmployees;
    private HistoryDTO history;
}
