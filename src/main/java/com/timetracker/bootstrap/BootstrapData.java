package com.timetracker.bootstrap;

import com.timetracker.entity.Employee;
import com.timetracker.entity.History;
import com.timetracker.entity.Task;
import com.timetracker.enums.Status;
import com.timetracker.repository.EmployeeRepository;
import com.timetracker.repository.HistoryRepository;
import com.timetracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final TaskRepository taskRepository;
    private final HistoryRepository historyRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {
        loadEntities();
    }

    private void loadEntities() {

        if (taskRepository.count() == 0) {
            Task firstTask = Task.builder()
                    .name("taskForAlexOnly")
                    .status(Status.CREATED.name())
                    .build();

            Task secondTask = Task.builder()
                    .name("taskForManyEmployees")
                    .status(Status.CREATED.name())
                    .build();

            Employee alex = Employee.builder()
                    .firstName("alex")
                    .lastName("someone")
                    .build();

            Employee kostya = Employee.builder()
                    .firstName("Kostya")
                    .lastName("someone")
                    .build();

            Employee maksim = Employee.builder()
                    .firstName("Maksim")
                    .lastName("someone")
                    .build();

            firstTask.addEmployee(alex);
            secondTask.addEmployee(kostya);
            secondTask.addEmployee(maksim);

            History history1 = History.builder().build();
            History history2 = History.builder().build();
            History history3 = History.builder().build();

            history1.addTask(firstTask);
            history2.addTask(secondTask);
            history3.addTask(secondTask);

            alex.addHistory(history1);
            kostya.addHistory(history2);
            maksim.addHistory(history3);

            historyRepository.saveAll(List.of(history1, history2, history3));
            employeeRepository.saveAll(List.of(alex, kostya, maksim));
            taskRepository.saveAll(List.of(firstTask, secondTask));
        }
    }
}
