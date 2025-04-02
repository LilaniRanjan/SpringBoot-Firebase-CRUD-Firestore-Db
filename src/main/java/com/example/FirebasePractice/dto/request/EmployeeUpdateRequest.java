package com.example.FirebasePractice.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateRequest {
    private String name;
    private String email;
    private String department;
}
