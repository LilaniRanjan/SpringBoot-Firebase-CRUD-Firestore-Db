package com.example.FirebasePractice.repository;

import com.example.FirebasePractice.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
