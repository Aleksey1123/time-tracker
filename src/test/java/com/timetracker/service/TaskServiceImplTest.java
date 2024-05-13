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

        given(pageBuilder.buildPageRequest(any(), any(), any(), any()))
                .willReturn(PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "id")));

        given(taskRepository.findAll((Pageable) any())).willReturn(tasks);

        Page<TaskDTO> dtoTasks = taskService.getAllTasks(0, 0, "id");

        assertEquals(1, dtoTasks.getSize());
    }

    @Test
    void getTaskById() {

        given(taskRepository.findById(1)).willReturn(Optional.of(testTask));
        TaskDTO actualTaskDTO = taskService.getTaskById(1);

        assertEquals(testTask.getName(), actualTaskDTO.getName());
    }

    @Test
    void getTaskByIdNotFound() {

        given(taskRepository.findById(1)).willReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1));
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

        given(taskRepository.findById(1)).willReturn(Optional.of(testTask));

        Task deletedTask = taskService.deleteTaskById(1);
        assertThat(deletedTask).isNotNull();
    }

    @Test
    void deleteTaskByIdNotFound() {

        given(taskRepository.findById(1)).willReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTaskById(1));
    }

    @Test
    void updateTaskById() {

        given(taskRepository.findById(1)).willReturn(Optional.of(testTask));

        Task updatedTask = taskService.updateTaskById(1, testDTOTask);
        assertEquals(testDTOTask.getName(), updatedTask.getName());
        assertEquals(testDTOTask.getStatus(), updatedTask.getStatus());
    }


    @Test
    void startTaskByIdNotFound() {

        given(taskRepository.findById(1)).willReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.startTaskById(1));
    }

    @Test
    void startTaskById() {

        given(taskRepository.findById(1)).willReturn(Optional.of(testTask));

        Task startedTask = taskService.startTaskById(1);
        assertEquals(startedTask.getStatus(), Status.STARTED.name());
    }

    @Test
    void suspendTaskByIdNotFound() {

        given(taskRepository.findById(1)).willReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.suspendTaskById(1));
    }

    @Test
    void suspendTaskById() {

        given(taskRepository.findById(1)).willReturn(Optional.of(testTask));

        Task suspendedTask = taskService.suspendTaskById(1);
        assertEquals(suspendedTask.getStatus(), Status.SUSPENDED.name());
    }

    @Test
    void endTaskByIdNotFound() {

        given(taskRepository.findById(1)).willReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.endTaskById(1));
    }

    @Test
    void endTaskById() {

        given(taskRepository.findById(1)).willReturn(Optional.of(testTask));

        Task endedTask = taskService.endTaskById(1);
        assertEquals(endedTask.getStatus(), Status.ENDED.name());
    }
}