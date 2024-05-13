package com.timetracker.service;

import com.timetracker.entity.Task;
import com.timetracker.model.TaskDTO;
import org.springframework.data.domain.Page;

public interface TaskService {

    Page<TaskDTO> getAllTasks(Integer pageNumber, Integer pageSize, String sortingParam);

    TaskDTO getTaskById(Integer id);

    Task saveNewTask(TaskDTO taskDTO);

    Task deleteTaskById(Integer taskId);

    Task updateTaskById(Integer taskId, TaskDTO taskDTO);

    Task startTaskById(Integer taskId);

    Task suspendTaskById(Integer taskId);

    Task endTaskById(Integer taskId);
}
