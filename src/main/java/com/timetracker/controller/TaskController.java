package com.timetracker.controller;

import com.timetracker.model.TaskDTO;
import com.timetracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public Page<TaskDTO> getAllTasks(@RequestParam(required = false) Integer pageNumber,
                                     @RequestParam(required = false) Integer pageSize,
                                     @RequestParam(required = false) String sortingParam) {

        return taskService.getAllTasks(pageNumber, pageSize, sortingParam);
    }

    @GetMapping("/{taskId}")
    public TaskDTO getTaskById(@PathVariable Integer taskId) {

        return taskService.getTaskById(taskId);
    }

    @PostMapping
    public ResponseEntity<String> saveNewTask(@RequestBody TaskDTO taskDTO) {

        taskService.saveNewTask(taskDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<String> deleteTaskById(@PathVariable Integer taskId) {

        taskService.deleteTaskById(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<String> updateTaskById(@PathVariable Integer taskId,
                                                 @RequestBody TaskDTO taskDTO) {

        taskService.updateTaskById(taskId, taskDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
