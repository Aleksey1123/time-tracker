package com.timetracker.mapper;

import com.timetracker.entity.Task;
import com.timetracker.enums.Status;
import com.timetracker.model.TaskDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TaskMapperTest {

    private Task task;
    private TaskDTO taskDTO;

    @BeforeEach
    void setUp() {

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now();
        Long startTime = System.currentTimeMillis();
        Long endTime = System.currentTimeMillis();

        task = Task.builder()
                .id(1)
                .name("testTask")
                .startDate(startDate)
                .endDate(endDate)
                .status(Status.CREATED.name())
                .startTime(startTime)
                .endTime(endTime)
                .build();

        taskDTO = TaskDTO.builder()
                .id(1)
                .name("testTask")
                .startDate(startDate)
                .endDate(endDate)
                .status(Status.CREATED.name())
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }

    @Test
    void taskToTaskDTO() {

        TaskDTO taskDTO = TaskMapper.INSTANCE.taskToTaskDTO(task);
        assertThat(task).usingRecursiveComparison()
                .isEqualTo(taskDTO);
    }

    @Test
    void taskDTOToTask() {

        Task task = TaskMapper.INSTANCE.taskDTOToTask(taskDTO);
        assertThat(taskDTO).usingRecursiveComparison()
                .isEqualTo(task);
    }
}