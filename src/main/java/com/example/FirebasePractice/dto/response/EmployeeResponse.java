package com.example.FirebasePractice.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private String id;
    private String name;
    private String email;
    private String department;
}
