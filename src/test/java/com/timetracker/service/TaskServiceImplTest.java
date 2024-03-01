package com.timetracker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.timetracker.entity.Employee;
import com.timetracker.entity.Task;
import com.timetracker.enums.EntityNames;
import com.timetracker.enums.Status;
import com.timetracker.exception.TaskNotFoundException;
import com.timetracker.model.TaskDTO;
import com.timetracker.repository.TaskRepository;
import com.timetracker.sorting.PageBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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
                .id(1)
                .name("testTask")
                .build();

        testDTOTask = TaskDTO.builder()
                .id(1)
                .name("testTask")
                .build();
    }

    @Test
    void getAllTasks() {
        Page<Task> tasks = new PageImpl<>(List.of(Task.builder().build()));
        PageRequest pageRequest = pageBuilder.buildPageRequest(0, 0,
                "id", EntityNames.Task.name());

        when(taskRepository.findAll(pageRequest)).thenReturn(tasks);

        Page<TaskDTO> dtoTasks = taskService.getAllTasks(0, 0, "id");

        assertEquals(1, dtoTasks.getSize());
    }

    @Test
    void getTaskById() {
        when(taskRepository.findById(1)).thenReturn(Optional.of(testTask));
        TaskDTO actualTaskDTO = taskService.getTaskById("1");

        assertEquals(testTask.getName(), actualTaskDTO.getName());
    }

    @Test
    void getTaskByIdNotFound() {
        when(taskRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById("1"));
    }

    @Test
    void postTask() throws Exception {

        given(taskRepository.save(testTask)).willReturn(testTask);
        System.out.println(taskRepository);

        Task savedTask = taskService.postTask(testDTOTask);
        System.out.println(savedTask + "!!!!!!!!!!!!!!!!");
        assertThat(savedTask).isNotNull();
    }
}