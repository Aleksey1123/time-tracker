package com.timetracker.repository;

import com.timetracker.entity.Employee;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByFirstNameAndLastName(@NotEmpty(message = "provide a first name") String firstName,
                                                  @NotEmpty(message = "provide a last name") String lastName);
}
