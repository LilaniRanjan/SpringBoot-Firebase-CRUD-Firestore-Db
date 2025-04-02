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

    // Firestore instance for accessing the Firebase Firestore database
    @Autowired
    private Firestore firestore;

    // Helper method to get the "employees" collection reference in Firestore
    private CollectionReference getEmployeeCollection() {
        return firestore.collection("employees"); // Reference to Firestore "employees" collection
    }

    //  Create a new Employee in Firestore
    @Override
    public EmployeeResponse createEmployee(EmployeeCreateRequest request) {
        Employee employee = new Employee();
        // Set employee details from the incoming request
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());

        try {
            // Create a new document reference in the "employees" collection
            DocumentReference docRef = getEmployeeCollection().document();
            // Set the generated document ID before saving the employee
            employee.setId(docRef.getId());

            // Save the employee object to Firestore with the generated ID
            docRef.set(employee).get();

            // Return the employee details as a response DTO
            return mapToResponse(employee);
        } catch (InterruptedException | ExecutionException e) {
            // Handle any exceptions that occur during the save operation
            throw new RuntimeException("Error creating employee: " + e.getMessage());
        }
    }

    //  Retrieve an Employee by ID from Firestore
    @Override
    public EmployeeResponse getEmployeeById(String id) {
        try {
            // Fetch the document by its ID
            var documentSnapshot = getEmployeeCollection().document(id).get().get();
            if (documentSnapshot.exists()) {
                // If document exists, map it to an Employee object and return the response
                Employee employee = documentSnapshot.toObject(Employee.class);
                employee.setId(id); // Set the Firestore document ID
                return mapToResponse(employee);
            } else {
                // If no employee is found, throw an exception
                throw new RuntimeException("Employee not found with ID: " + id);
            }
        } catch (InterruptedException | ExecutionException e) {
            // Handle any exceptions during fetching the employee
            throw new RuntimeException("Failed to fetch employee: " + e.getMessage());
        }
    }

    //  Retrieve all Employees from Firestore
    @Override
    public List<EmployeeResponse> getAllEmployees() {
        List<EmployeeResponse> employeeResponses = new ArrayList<>();
        try {
            // Get all documents in the "employees" collection
            var querySnapshot = getEmployeeCollection().get().get();
            for (QueryDocumentSnapshot snapshot : querySnapshot.getDocuments()) {
                // For each document, map it to an Employee object and add to the response list
                Employee employee = snapshot.toObject(Employee.class);
                employee.setId(snapshot.getId()); // Set Firestore document ID
                employeeResponses.add(mapToResponse(employee)); // Map to response DTO and add
            }
        } catch (InterruptedException | ExecutionException e) {
            // Handle any exceptions during the retrieval of all employees
            throw new RuntimeException("Failed to fetch employees: " + e.getMessage());
        }
        return employeeResponses; // Return the list of employee responses
    }

    //  Delete an Employee by ID from Firestore
    @Override
    public void deleteEmployee(String id) {
        try {
            // Delete the document by its ID from the "employees" collection
            getEmployeeCollection().document(id).delete().get();
            System.out.println("Employee with ID " + id + " deleted successfully.");
        } catch (InterruptedException | ExecutionException e) {
            // Handle any exceptions during the delete operation
            throw new RuntimeException("Failed to delete employee: " + e.getMessage());
        }
    }

    //  Update an existing Employee in Firestore
    @Override
    public EmployeeResponse updateEmployee(String id, EmployeeCreateRequest request) {
        try {
            // Get the document reference by employee ID
            DocumentReference docRef = getEmployeeCollection().document(id);
            var docSnapshot = docRef.get().get(); // Fetch the document

            if (docSnapshot.exists()) {
                // If the document exists, update it with the new details from the request
                Employee employee = new Employee();
                employee.setId(id); // Set the employee ID
                employee.setName(request.getName());
                employee.setEmail(request.getEmail());
                employee.setDepartment(request.getDepartment());

                // Replace the existing document with the new employee data
                docRef.set(employee).get();

                // Return the updated employee details as a response DTO
                return mapToResponse(employee);
            } else {
                // If the employee does not exist, throw an exception
                throw new RuntimeException("Employee not found with ID: " + id);
            }
        } catch (InterruptedException | ExecutionException e) {
            // Handle any exceptions during the update operation
            throw new RuntimeException("Failed to update employee: " + e.getMessage());
        }
    }

    //  Convert Employee to EmployeeResponse DTO
    private EmployeeResponse mapToResponse(Employee employee) {
        EmployeeResponse response = new EmployeeResponse();
        // Map the Employee object fields to the response DTO
        response.setId(employee.getId());
        response.setName(employee.getName());
        response.setEmail(employee.getEmail());
        response.setDepartment(employee.getDepartment());
        return response; // Return the EmployeeResponse DTO
    }
}
