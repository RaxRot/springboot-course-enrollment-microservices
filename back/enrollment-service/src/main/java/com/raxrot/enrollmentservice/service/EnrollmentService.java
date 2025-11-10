package com.raxrot.enrollmentservice.service;

import com.raxrot.enrollmentservice.dto.EnrollmentRequest;
import com.raxrot.enrollmentservice.dto.EnrollmentResponse;

import java.util.List;

public interface EnrollmentService {
    EnrollmentResponse enroll(EnrollmentRequest request);
    List<EnrollmentResponse> getEnrollmentsByUser(Long userId);
    List<EnrollmentResponse> getEnrollmentsByCourse(Long courseId);
}
