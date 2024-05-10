package com.timetracker.controller;

import com.timetracker.model.EmployeeDTO;
import com.timetracker.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public Page<EmployeeDTO> getAllTasks(@RequestParam(required = false) Integer pageNumber,
                                         @RequestParam(required = false) Integer pageSize,
                                         @RequestParam(required = false) String sortingParam) {

        return employeeService.getAllEmployees(pageNumber, pageSize, sortingParam);
    }

    @GetMapping("/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable Integer employeeId) {

        return employeeService.getEmployeeById(employeeId);
    }

    @PostMapping
    public ResponseEntity<String> saveNewEmployee(@RequestBody EmployeeDTO employeeDTO) {

        employeeService.saveNewEmployee(employeeDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{employeeId}")
    public ResponseEntity<String> deleteEmployeeById(@PathVariable Integer employeeId) {

        employeeService.deleteEmployeeById(employeeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<String> updateTaskById(@PathVariable Integer employeeId,
                                                 @RequestBody EmployeeDTO employeeDTO) {

        employeeService.updateEmployeeById(employeeId, employeeDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
