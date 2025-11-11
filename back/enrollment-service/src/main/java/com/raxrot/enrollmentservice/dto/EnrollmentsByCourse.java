package com.raxrot.enrollmentservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class EnrollmentsByCourse {
    private CourseResponse courseResponse;
    private List<EnrollmentResponse> enrollments;
}
