package com.raxrot.enrollmentservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class EnrollmentsByUser {
    private UserResponse userResponse;
    private List<EnrollmentResponse> enrollments;
}
