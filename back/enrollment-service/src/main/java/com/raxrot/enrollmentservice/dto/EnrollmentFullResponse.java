package com.raxrot.enrollmentservice.dto;

import lombok.Data;

@Data
public class EnrollmentFullResponse {
    private EnrollmentResponse enrollmentResponse;
    private UserResponse user;
    private CourseResponse course;
}
