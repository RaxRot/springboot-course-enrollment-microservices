package com.raxrot.enrollmentservice.service;

import com.raxrot.enrollmentservice.dto.EnrollmentRequest;
import com.raxrot.enrollmentservice.dto.EnrollmentResponse;
import com.raxrot.enrollmentservice.exceptions.ApiException;
import com.raxrot.enrollmentservice.mapper.EnrollmentMapper;
import com.raxrot.enrollmentservice.model.Enrollment;
import com.raxrot.enrollmentservice.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public EnrollmentResponse enroll(EnrollmentRequest request) {
        boolean alreadyExists = enrollmentRepository
                .existsByUserIdAndCourseId(request.getUserId(), request.getCourseId());
        if (alreadyExists) {
            throw new ApiException("User already enrolled in this course", HttpStatus.CONFLICT);
        }
        Enrollment enrollment= EnrollmentMapper.mapToEnroll(request);
        Enrollment savedEnrollment=enrollmentRepository.save(enrollment);

        return EnrollmentMapper.mapToEnrollResponse(savedEnrollment);
    }

    @Override
    public List<EnrollmentResponse> getEnrollmentsByUser(Long userId) {
        List<Enrollment>enrollments=enrollmentRepository.findByUserId(userId);
        List<EnrollmentResponse>enrollmentResponses=enrollments.stream()
        .map(enrollment->EnrollmentMapper.mapToEnrollResponse(enrollment))
        .toList();
        return enrollmentResponses;
    }

    @Override
    public List<EnrollmentResponse> getEnrollmentsByCourse(Long courseId) {
        List<Enrollment>enrollments=enrollmentRepository.findByCourseId(courseId);
        List<EnrollmentResponse>enrollmentResponses=enrollments.stream()
                .map(enrollment -> EnrollmentMapper.mapToEnrollResponse(enrollment))
                .toList();
        return enrollmentResponses;
    }
}
