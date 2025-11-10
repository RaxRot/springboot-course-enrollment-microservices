package com.raxrot.enrollmentservice.mapper;

import com.raxrot.enrollmentservice.dto.EnrollmentRequest;
import com.raxrot.enrollmentservice.dto.EnrollmentResponse;
import com.raxrot.enrollmentservice.model.Enrollment;

public class EnrollmentMapper {
    public static Enrollment mapToEnroll(EnrollmentRequest request) {
        return Enrollment.builder()
                .userId(request.getUserId())
                .courseId(request.getCourseId())
                .build();
    }

    public static EnrollmentResponse mapToEnrollResponse(Enrollment enrollment) {
        return EnrollmentResponse.builder()
                .id(enrollment.getId())
                .userId(enrollment.getUserId())
                .courseId(enrollment.getCourseId())
                .build();
    }
}
