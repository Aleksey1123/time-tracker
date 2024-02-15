package com.timetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @NotEmpty(message = "provide a first name")
    private String firstName;
    @NotEmpty(message = "provide a last name")
    private String lastName;

    @ManyToOne(cascade = CascadeType.ALL)
    private Task currentTask;
}
