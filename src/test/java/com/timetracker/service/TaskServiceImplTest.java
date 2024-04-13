package com.timetracker.service;

import com.timetracker.entity.Task;
import com.timetracker.enums.Status;
import com.timetracker.exception.TaskAlreadyExistsException;
import com.timetracker.exception.TaskNotFoundException;
import com.timetracker.model.TaskDTO;
import com.timetracker.repository.TaskRepository;
import com.timetracker.sorting.PageBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private PageBuilder pageBuilder;
    @InjectMocks
    private TaskServiceImpl taskService;

    private Task testTask;
    private TaskDTO testDTOTask;


    @BeforeEach
    void setUp() {

        testTask = Task.builder()
                .id(100)
                .name("testTask")
                .status(Status.CREATED.name())
                .build();

        testDTOTask = TaskDTO.builder()
                .id(100)
                .name("testTask")
                .status(Status.CREATED.name())
                .build();
    }

    @Test
    void getAllTasks() {
        Page<Task> tasks = new PageImpl<>(List.of(Task.builder().build()));

        when(pageBuilder.buildPageRequest(any(), any(), any(), any()))
                .thenReturn(PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "id")));

        when(taskRepository.findAll((Pageable) any())).thenReturn(tasks);

        Page<TaskDTO> dtoTasks = taskService.getAllTasks(0, 0, "id");

        assertEquals(1, dtoTasks.getSize());
    }

    @Test
    void getTaskById() {
        when(taskRepository.findById(testTask.getId())).thenReturn(Optional.of(testTask));
        TaskDTO actualTaskDTO = taskService.getTaskById(testTask.getId());

        assertEquals(testTask.getName(), actualTaskDTO.getName());
    }

    @Test
    void getTaskByIdNotFound() {
        when(taskRepository.findById(testTask.getId())).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(testTask.getId()));
    }

    @Test
    void saveNewTask() {

        given(taskRepository.findByName(any())).willReturn(Optional.empty());
        given(taskRepository.save(any())).willReturn(testTask);

        Task savedTask = taskService.saveNewTask(testDTOTask);
        assertThat(savedTask).isNotNull();
    }

    @Test
    void saveNewTaskAlreadyExists() {

        given(taskRepository.findByName(any())).willReturn(Optional.of(testTask));

        assertThrows(TaskAlreadyExistsException.class, () -> taskService.saveNewTask(testDTOTask));
    }

    @Test
    void deleteTaskById() {

        given(taskRepository.findById(testTask.getId())).willReturn(Optional.of(testTask));

        Task deletedTask = taskService.deleteTaskById(testTask.getId());
        assertThat(deletedTask).isNotNull();
    }

    @Test
    void deleteTaskByIdNotFound() {

        given(taskRepository.findById(testTask.getId())).willReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTaskById(testTask.getId()));
    }

    @Test
    void updateTaskById() {

        TaskDTO newDTOTask = TaskDTO.builder()
                .name("newTask")
                .status(Status.STARTED.name())
                .build();

        given(taskRepository.findById(testTask.getId())).willReturn(Optional.of(testTask));
        given(taskRepository.save(any(Task.class))).willReturn(testTask);

        Task savedTask = taskService.updateTaskById(testTask.getId(), newDTOTask);
        assertEquals(newDTOTask.getName(), savedTask.getName());
        assertEquals(newDTOTask.getStatus(), savedTask.getStatus());
    }
}