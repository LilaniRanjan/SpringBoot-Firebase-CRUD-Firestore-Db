package com.example.FirebasePractice.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateRequest {
    private String name;
    private String email;
    private String department;
}
