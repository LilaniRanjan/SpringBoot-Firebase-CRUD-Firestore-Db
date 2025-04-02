package com.example.FirebasePractice.service;

import com.example.FirebasePractice.dto.request.EmployeeCreateRequest;
import com.example.FirebasePractice.dto.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(EmployeeCreateRequest request);

    EmployeeResponse getEmployeeById(String id);

    List<EmployeeResponse> getAllEmployees();

    EmployeeResponse updateEmployee(String id, EmployeeCreateRequest request);

    void deleteEmployee(String id);
}
