package com.timetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
@Table(name = "employee")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "provide a first name")
    private String firstName;
    @NotEmpty(message = "provide a last name")
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Task currentTask;
}
