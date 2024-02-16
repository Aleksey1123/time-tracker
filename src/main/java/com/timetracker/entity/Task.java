package com.timetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@Table(name = "tasks")
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_id")
    private Integer id;

    @NotEmpty(message = "provide a name")
    @Column(name = "task_name")
    private String name;

    @Column(name = "task_start_time")
    private Long startTime;
    @Column(name = "task_start_date")
    private LocalDateTime startDate;
    @Column(name = "task_end_time")
    private Long endTime;
    @Column(name = "task_end_date")
    private LocalDateTime endDate;
    @Column(name = "task_status")
    private String status;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Employee> assignedEmployees;
}
