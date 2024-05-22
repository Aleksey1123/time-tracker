package com.timetracker.service;

import com.timetracker.entity.Employee;
import com.timetracker.entity.History;
import com.timetracker.entity.Task;
import com.timetracker.enums.EntityNames;
import com.timetracker.enums.Status;
import com.timetracker.exception.EmployeeNotFoundException;
import com.timetracker.exception.TaskAlreadyExistsException;
import com.timetracker.exception.TaskNotFoundException;
import com.timetracker.mapper.TaskMapper;
import com.timetracker.model.EmployeeDTO;
import com.timetracker.model.TaskDTO;
import com.timetracker.repository.EmployeeRepository;
import com.timetracker.repository.HistoryRepository;
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
    private final EmployeeRepository employeeRepository;
    private final PageBuilder pageBuilder;
    private final TaskMapper taskMapper = TaskMapper.INSTANCE;

    private static final String EMPLOYEE_NOT_FOUND_EXC_MESSAGE = "employee not found";
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

        Task foundTask = taskRepository.findById(id).orElseThrow(
                () -> new TaskNotFoundException(TASK_NOT_FOUND_EXC_MESSAGE));

        return taskMapper.taskToTaskDTO(foundTask);
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

        Task foundTask = taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException(TASK_NOT_FOUND_EXC_MESSAGE));
        taskRepository.deleteById(taskId);

        return foundTask;
    }

    @Override
    public Task updateTaskById(Integer taskId, TaskDTO taskDTO) {

        Task foundTask = taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException(TASK_NOT_FOUND_EXC_MESSAGE));
        foundTask.setName(taskDTO.getName());
        foundTask.setStatus(taskDTO.getStatus());

        return foundTask;
    }

    @Override
    public Task delegateTaskById(Integer taskId, EmployeeDTO employeeDTO) {

        Task foundTask = taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException(TASK_NOT_FOUND_EXC_MESSAGE));

        Employee foundEmployee = employeeRepository.findByFirstNameAndLastName(
                employeeDTO.getFirstName(), employeeDTO.getLastName())
                .orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE_NOT_FOUND_EXC_MESSAGE));

        foundTask.addEmployee(foundEmployee);
        History employeeHistory = foundEmployee.getHistory();
        employeeHistory.getHistoryList().add(foundTask);
        employeeHistory.setLastModified(LocalDateTime.now());
        foundTask.setHistory(employeeHistory);

        return foundTask;
    }

    @Override
    public Task startTaskById(Integer taskId) {

        Task foundTask = taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException(TASK_NOT_FOUND_EXC_MESSAGE));
        foundTask.setStatus(Status.STARTED.name());

        return foundTask;
    }

    @Override
    public Task suspendTaskById(Integer taskId) {

        Task foundTask = taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException(TASK_NOT_FOUND_EXC_MESSAGE));
        foundTask.setStatus(Status.SUSPENDED.name());

        return foundTask;
    }

    @Override
    public Task endTaskById(Integer taskId) {

        Task foundTask = taskRepository.findById(taskId).orElseThrow(
                () -> new TaskNotFoundException(TASK_NOT_FOUND_EXC_MESSAGE));
        foundTask.setStatus(Status.ENDED.name());
        foundTask.setEndDate(LocalDateTime.now());

        return foundTask;
    }
}