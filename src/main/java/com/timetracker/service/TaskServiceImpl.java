package com.timetracker.service;

import com.timetracker.entity.Task;
import com.timetracker.enums.EntityNames;
import com.timetracker.enums.Status;
import com.timetracker.exception.TaskNotFoundException;
import com.timetracker.mapper.TaskMapper;
import com.timetracker.model.TaskDTO;
import com.timetracker.repository.TaskRepository;
import com.timetracker.sorting.PageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final PageBuilder pageBuilder;
    private final TaskMapper taskMapper = TaskMapper.INSTANCE;

    private static final String TASK_NOT_FOUND_EXC_MESSAGE = "task not found";

    @Override
    public Page<TaskDTO> getAllTasks(Integer pageNumber, Integer pageSize,
                                     String sortingParam) {
        PageRequest pageRequest = pageBuilder.buildPageRequest(pageNumber, pageSize,
                sortingParam, EntityNames.Task.name());

        return new PageImpl<>(taskRepository.findAll(pageRequest)
                .stream()
                .map(taskMapper::taskToTaskDTO)
                .collect(Collectors.toList()));
    }

    @Override
    public TaskDTO getTaskById(String id) {

        Optional<Task> optionalTask = taskRepository.findById(Integer.parseInt(id));
        if (optionalTask.isEmpty())
            throw new TaskNotFoundException(TASK_NOT_FOUND_EXC_MESSAGE);
        return optionalTask.map(taskMapper::taskToTaskDTO).get();
    }

    @Override
    public Task postTask(TaskDTO taskDTO) {

        Task task = taskMapper.taskDTOToTask(taskDTO);
        task.setStartDate(LocalDateTime.now());
        task.setStatus(Status.CREATED.name());

        return taskRepository.save(task);
    }
}