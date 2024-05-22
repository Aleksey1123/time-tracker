package com.timetracker.controller;

import com.timetracker.model.EmployeeDTO;
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

    @PutMapping("/delegateTask/{taskId}")
    public ResponseEntity<String> delegateTask(@PathVariable Integer taskId,
                                               @RequestBody EmployeeDTO employeeDTO) {

        taskService.delegateTaskById(taskId, employeeDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<String> updateTaskById(@PathVariable Integer taskId,
                                                 @RequestBody TaskDTO taskDTO) {

        taskService.updateTaskById(taskId, taskDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/startTask/{taskId}")
    public ResponseEntity<String> startTaskById(@PathVariable Integer taskId) {

        taskService.startTaskById(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/suspendTask/{taskId}")
    public ResponseEntity<String> suspendTaskById(@PathVariable Integer taskId) {

        taskService.suspendTaskById(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/endTask/{taskId}")
    public ResponseEntity<String> endTaskById(@PathVariable Integer taskId) {

        taskService.endTaskById(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
