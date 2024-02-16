package com.timetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "employees")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id")
    private Integer id;

    @NotEmpty(message = "provide a first name")
//    @Column(name = "employee_name")
    private String firstName;
    @NotEmpty(message = "provide a last name")
//    @Column(name = "employee_last_name")
    private String lastName;

//    @Column(name = "employee_current_task")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id")
    private Task currentTask;
}
