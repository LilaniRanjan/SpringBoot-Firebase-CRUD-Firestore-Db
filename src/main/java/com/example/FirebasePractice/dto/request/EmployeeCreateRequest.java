package com.example.FirebasePractice.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateRequest {
    private String name;
    private String email;
    private String department;
    private String firebaseUid;
}
