package com.timetracker.service;

import com.timetracker.entity.Task;
import com.timetracker.model.TaskDTO;
import org.springframework.data.domain.Page;

public interface TaskService {

    Page<TaskDTO> getAllTasks(Integer pageNumber, Integer pageSize, String sortingParam);

    TaskDTO getTaskById(String id);

    Task saveNewTask(TaskDTO taskDTO);

    Task deleteTaskById(Integer taskId);

    Task updateTaskById(Integer taskId, TaskDTO taskDTO);
}
