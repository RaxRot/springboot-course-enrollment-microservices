package com.raxrot.enrollmentservice.service;

import com.raxrot.enrollmentservice.dto.EnrollmentFullResponse;
import com.raxrot.enrollmentservice.dto.EnrollmentRequest;
import com.raxrot.enrollmentservice.dto.EnrollmentsByCourse;
import com.raxrot.enrollmentservice.dto.EnrollmentsByUser;

public interface EnrollmentService {
    EnrollmentFullResponse enroll(EnrollmentRequest request);
    EnrollmentsByUser getEnrollmentsByUser(Long userId);
    EnrollmentsByCourse getEnrollmentsByCourse(Long courseId);
}
