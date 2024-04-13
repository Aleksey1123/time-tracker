package com.timetracker.service;

import com.timetracker.entity.Task;
import com.timetracker.model.TaskDTO;
import org.springframework.data.domain.Page;

public interface TaskService {

    Task findTaskOrThrowNotFoundException(Integer taskId);

    Page<TaskDTO> getAllTasks(Integer pageNumber, Integer pageSize, String sortingParam);

    TaskDTO getTaskById(Integer id);

    Task saveNewTask(TaskDTO taskDTO);

    Task deleteTaskById(Integer taskId);

    Task updateTaskById(Integer taskId, TaskDTO taskDTO);
}
