package com.timetracker.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EmployeeDTO {

    private Integer id;

    private String firstName;
    private String lastName;

    private TaskDTO currentTask;
    private HistoryDTO history;
}
