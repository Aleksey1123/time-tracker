package com.timetracker.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
public class HistoryDTO {

    private Integer id;
    private LocalDateTime lastModified;

    private List<TaskDTO> historyList;
    private EmployeeDTO employee;
}
