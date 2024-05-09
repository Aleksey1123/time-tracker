package com.timetracker.service;

import com.timetracker.entity.Task;
import com.timetracker.enums.EntityNames;
import com.timetracker.enums.Status;
import com.timetracker.exception.TaskAlreadyExistsException;
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
    private static final String TASK_ALREADY_EXISTS_EXC_MESSAGE = "task already exists, provide a different name";

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
    public TaskDTO getTaskById(Integer id) {

        Optional<Task> optionalTask = taskRepository.findById(id);
        if (optionalTask.isEmpty())
            throw new TaskNotFoundException(TASK_NOT_FOUND_EXC_MESSAGE);
        return optionalTask.map(taskMapper::taskToTaskDTO).get();
    }

    @Override
    public Task saveNewTask(TaskDTO taskDTO) {

        if (taskRepository.findByName(taskDTO.getName()).isPresent())
            throw new TaskAlreadyExistsException(TASK_ALREADY_EXISTS_EXC_MESSAGE);

        Task task = taskMapper.taskDTOToTask(taskDTO);
        task.setStartDate(LocalDateTime.now());
        task.setStatus(Status.CREATED.name());

        return taskRepository.save(task);
    }

    @Override
    public Task deleteTaskById(Integer taskId) {

        Optional<Task> taskOptional = taskRepository.findById(taskId);
        if (taskOptional.isEmpty())
            throw new TaskNotFoundException(TASK_NOT_FOUND_EXC_MESSAGE);
        taskRepository.deleteById(taskId);
        return taskOptional.get();
    }

    @Override
    public Task updateTaskById(Integer taskId, TaskDTO taskDTO) {

        Task foundTask = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        foundTask.setName(taskDTO.getName());
        foundTask.setStatus(taskDTO.getStatus());
        return foundTask;
    }
}