package com.timetracker.service;

import com.timetracker.entity.Task;
import com.timetracker.enums.EntityNames;
import com.timetracker.mapper.TaskMapper;
import com.timetracker.model.TaskDTO;
import com.timetracker.repository.TaskRepository;
import com.timetracker.sorting.PageBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;
    @Spy
    private PageBuilder pageBuilder;
    @InjectMocks
    private TaskServiceImpl taskService;


    @BeforeEach
    void setUp() {
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
}