package com.example.FirebasePractice.controller;

import com.example.FirebasePractice.dto.request.EmployeeCreateRequest;
import com.example.FirebasePractice.dto.response.EmployeeResponse;
import com.example.FirebasePractice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 🔹 Create Employee
    @PostMapping("/create")
    public EmployeeResponse createEmployee(@RequestBody EmployeeCreateRequest request) {
        return employeeService.createEmployee(request);
    }

    // 🔹 Get Employee by ID
    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable String id) {
        return employeeService.getEmployeeById(id);
    }

    // 🔹 Get All Employees
    @GetMapping("/all")
    public List<EmployeeResponse> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PutMapping("/update/{id}")
    public EmployeeResponse updateEmployee(@PathVariable String id, @RequestBody EmployeeCreateRequest request) {
        return employeeService.updateEmployee(id, request);
    }

    // 🔹 Delete Employee by ID
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return "Employee with ID " + id + " deleted successfully.";
    }
}
