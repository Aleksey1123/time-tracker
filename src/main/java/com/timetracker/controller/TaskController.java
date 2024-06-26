package com.timetracker.controller;

import com.timetracker.model.TaskDTO;
import com.timetracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employee")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public Page<TaskDTO> getAllTasks(@RequestParam(required = false) Integer pageNumber,
                                     @RequestParam(required = false) Integer pageSize,
                                     @RequestParam(required = false) String sortingParam) {

        return taskService.getAllTasks(pageNumber, pageSize, sortingParam);
    }
}
