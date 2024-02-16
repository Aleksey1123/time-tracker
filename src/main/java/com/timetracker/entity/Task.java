package com.timetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "provide a name")
    private String name;

    private Long startTime;
    private LocalDateTime startDate;
    private Long endTime;
    private LocalDateTime endDate;
    private String status;

    @OneToMany(
            mappedBy = "currentTask",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Employee> assignedEmployees = new ArrayList<>();

    public void addEmployee(Employee employee) {
        assignedEmployees.add(employee);
        employee.setCurrentTask(this);
    }

    public void removeEmployee(Employee employee) {
        assignedEmployees.remove(employee);
        employee.setCurrentTask(null);
    }
}
