package com.timetracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private LocalDateTime lastModified;

    @OneToMany(
            mappedBy = "history",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Task> historyList;

    @OneToOne(
            mappedBy = "history"
    )
    private Employee employee;

    public void addTask(Task task) {
        if (historyList == null)
            historyList = new ArrayList<>();

        historyList.add(task);
        task.setHistory(this);
    }
}
