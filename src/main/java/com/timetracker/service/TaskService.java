package com.timetracker.service;

import com.timetracker.model.TaskDTO;
import org.springframework.data.domain.Page;

public interface TaskService {

    Page<TaskDTO> getAllTasks(Integer pageNumber, Integer pageSize, String sortingParam);
}
