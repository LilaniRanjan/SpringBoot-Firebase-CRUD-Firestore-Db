package com.example.FirebasePractice.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeUpdateRequest {
    private String name;
    private String email;
    private String department;
}
