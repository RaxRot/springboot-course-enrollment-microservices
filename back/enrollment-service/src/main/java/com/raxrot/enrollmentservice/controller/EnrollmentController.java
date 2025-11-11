package com.raxrot.enrollmentservice.controller;

import com.raxrot.enrollmentservice.dto.EnrollmentFullResponse;
import com.raxrot.enrollmentservice.dto.EnrollmentRequest;
import com.raxrot.enrollmentservice.dto.EnrollmentsByCourse;
import com.raxrot.enrollmentservice.dto.EnrollmentsByUser;
import com.raxrot.enrollmentservice.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentFullResponse> enroll(@Valid @RequestBody EnrollmentRequest request) {
        return new ResponseEntity<>(enrollmentService.enroll(request), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<EnrollmentsByUser> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByUser(userId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<EnrollmentsByCourse> getByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByCourse(courseId));
    }
}