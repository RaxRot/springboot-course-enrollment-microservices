package com.raxrot.enrollmentservice.service;

import com.raxrot.enrollmentservice.client.CourseClient;
import com.raxrot.enrollmentservice.client.UserClient;
import com.raxrot.enrollmentservice.dto.*;
import com.raxrot.enrollmentservice.exceptions.ApiException;
import com.raxrot.enrollmentservice.mapper.EnrollmentMapper;
import com.raxrot.enrollmentservice.model.Enrollment;
import com.raxrot.enrollmentservice.repository.EnrollmentRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    private final UserClient userClient;
    private final CourseClient courseClient;

    @Override
    public EnrollmentFullResponse enroll(EnrollmentRequest request) {
        boolean alreadyExists = enrollmentRepository
                .existsByUserIdAndCourseId(request.getUserId(), request.getCourseId());
        if (alreadyExists) {
            throw new ApiException("User already enrolled in this course", HttpStatus.CONFLICT);
        }

        UserResponse userResponse;
        CourseResponse courseResponse;

        try {
            userResponse = userClient.getUserById(request.getUserId());
        } catch (FeignException.NotFound e) {
            throw new ApiException("User not found with id " + request.getUserId(), HttpStatus.NOT_FOUND);
        } catch (FeignException e) {
            throw new ApiException("User service unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        }

        try {
            courseResponse = courseClient.getCourseById(request.getCourseId());
        } catch (FeignException.NotFound e) {
            throw new ApiException("Course not found with id " + request.getCourseId(), HttpStatus.NOT_FOUND);
        } catch (FeignException e) {
            throw new ApiException("Course service unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        }

        Enrollment enrollment= EnrollmentMapper.mapToEnroll(request);
        Enrollment savedEnrollment=enrollmentRepository.save(enrollment);

        EnrollmentResponse enrollmentResponse=EnrollmentMapper.mapToEnrollResponse(savedEnrollment);

        EnrollmentFullResponse enrollmentFullResponse=new EnrollmentFullResponse();
        enrollmentFullResponse.setEnrollmentResponse(enrollmentResponse);
        enrollmentFullResponse.setUser(userResponse);
        enrollmentFullResponse.setCourse(courseResponse);

        return enrollmentFullResponse;
    }

    @Override
    public EnrollmentsByUser getEnrollmentsByUser(Long userId) {

        UserResponse userResponse;
        try {
            userResponse = userClient.getUserById(userId);
        } catch (FeignException.NotFound e) {
            throw new ApiException("User not found with id " + userId, HttpStatus.NOT_FOUND);
        }

        List<Enrollment>enrollments=enrollmentRepository.findByUserId(userId);
        List<EnrollmentResponse>enrollmentResponses=enrollments.stream()
        .map(enrollment->EnrollmentMapper.mapToEnrollResponse(enrollment))
        .toList();

        EnrollmentsByUser enrollmentsByUser=new EnrollmentsByUser();
        enrollmentsByUser.setEnrollments(enrollmentResponses);
        enrollmentsByUser.setUserResponse(userResponse);
        return enrollmentsByUser;
    }

    @Override
    public EnrollmentsByCourse getEnrollmentsByCourse(Long courseId) {
        try {
            courseClient.getCourseById(courseId);
        } catch (FeignException.NotFound e) {
            throw new ApiException("Course not found with id " + courseId, HttpStatus.NOT_FOUND);
        }
        List<Enrollment>enrollments=enrollmentRepository.findByCourseId(courseId);
        List<EnrollmentResponse>enrollmentResponses=enrollments.stream()
                .map(enrollment -> EnrollmentMapper.mapToEnrollResponse(enrollment))
                .toList();
        EnrollmentsByCourse enrollmentsByCourse=new EnrollmentsByCourse();
        enrollmentsByCourse.setEnrollments(enrollmentResponses);
        enrollmentsByCourse.setCourseResponse(courseClient.getCourseById(courseId));
        return enrollmentsByCourse;
    }
}
