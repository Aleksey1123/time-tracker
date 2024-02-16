package com.timetracker.bootstrap;

import com.timetracker.entity.Employee;
import com.timetracker.entity.Task;
import com.timetracker.enums.Status;
import com.timetracker.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final TaskRepository taskRepository;

    @Override
    public void run(String... args) throws Exception {
        loadEntities();
    }

    private void loadEntities() {

        if (taskRepository.count() == 0) {
            Task firstTask = Task.builder()
                    .name("taskForAlexOnly")
                    .assignedEmployees(new ArrayList<>())
                    .status(Status.CREATED.name())
                    .build();

            Task secondTask = Task.builder()
                    .name("taskForManyEmployees")
                    .assignedEmployees(new ArrayList<>())
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

            taskRepository.saveAll(List.of(firstTask, secondTask));
        }
    }
}
