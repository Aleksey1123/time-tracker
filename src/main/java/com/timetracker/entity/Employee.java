package com.timetracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "history_id", referencedColumnName = "id")
    private History history;

    public void addHistory(History history) {
        this.setHistory(history);
        history.setEmployee(this);
    }
}
