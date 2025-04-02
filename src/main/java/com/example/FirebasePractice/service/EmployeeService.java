package com.example.FirebasePractice.service;

import com.example.FirebasePractice.dto.request.EmployeeCreateRequest;
import com.example.FirebasePractice.dto.request.EmployeeUpdateRequest;
import com.example.FirebasePractice.dto.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse createEmployee(EmployeeCreateRequest request);
    EmployeeResponse getEmployeeById(Long id);
    List<EmployeeResponse> getAllEmployees();
    EmployeeResponse updateEmployee(Long id, EmployeeUpdateRequest request);
    void deleteEmployee(Long id);
}
