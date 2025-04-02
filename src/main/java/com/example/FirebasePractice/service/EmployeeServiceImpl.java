package com.example.FirebasePractice.service;

import com.example.FirebasePractice.dto.request.EmployeeCreateRequest;
import com.example.FirebasePractice.dto.response.EmployeeResponse;
import com.example.FirebasePractice.model.Employee;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private Firestore firestore;

    private CollectionReference getEmployeeCollection() {
        return firestore.collection("employees"); // Reference to Firestore "employees" collection
    }

    @Override
    public EmployeeResponse createEmployee(EmployeeCreateRequest request) {
        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());

        try {
            // Firestore generates a document with an auto-generated ID
            DocumentReference docRef = getEmployeeCollection().document();
            employee.setId(docRef.getId()); // Set the generated ID before saving

            // Save employee with the assigned ID
            docRef.set(employee).get();

            return mapToResponse(employee);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error creating employee: " + e.getMessage());
        }
    }


    // 🔹 Get Employee by ID
    @Override
    public EmployeeResponse getEmployeeById(String id) {
        try {
            var documentSnapshot = getEmployeeCollection().document(id).get().get();
            if (documentSnapshot.exists()) {
                Employee employee = documentSnapshot.toObject(Employee.class);
                employee.setId(id);
                return mapToResponse(employee);
            } else {
                throw new RuntimeException("Employee not found with ID: " + id);
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to fetch employee: " + e.getMessage());
        }
    }

    // 🔹 Get All Employees
    @Override
    public List<EmployeeResponse> getAllEmployees() {
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        try {
            var querySnapshot = getEmployeeCollection().get().get();
            for (QueryDocumentSnapshot snapshot : querySnapshot.getDocuments()) {
                Employee employee = snapshot.toObject(Employee.class);
                employee.setId(snapshot.getId()); // Assign Firestore document ID
                employeeResponses.add(mapToResponse(employee));
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to fetch employees: " + e.getMessage());
        }
        return employeeResponses;
    }

    // 🔹 Delete Employee by ID
    @Override
    public void deleteEmployee(String id) {
        try {
            getEmployeeCollection().document(id).delete().get();
            System.out.println("Employee with ID " + id + " deleted successfully.");
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to delete employee: " + e.getMessage());
        }
    }

    @Override
    public EmployeeResponse updateEmployee(String id, EmployeeCreateRequest request) {
        try {
            DocumentReference docRef = getEmployeeCollection().document(id);
            var docSnapshot = docRef.get().get();

            if (docSnapshot.exists()) {
                // Update the existing document
                Employee employee = new Employee();
                employee.setId(id);
                employee.setName(request.getName());
                employee.setEmail(request.getEmail());
                employee.setDepartment(request.getDepartment());

                docRef.set(employee).get(); // Firestore replaces the existing document
                return mapToResponse(employee);
            } else {
                throw new RuntimeException("Employee not found with ID: " + id);
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failed to update employee: " + e.getMessage());
        }
    }

    // 🔹 Convert Employee to EmployeeResponse DTO
    private EmployeeResponse mapToResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setEmail(employee.getEmail());
        response.setDepartment(employee.getDepartment());
        return response;
    }
}
