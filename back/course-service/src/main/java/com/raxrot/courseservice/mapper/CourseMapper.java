package com.raxrot.courseservice.mapper;

import com.raxrot.courseservice.dto.CourseRequest;
import com.raxrot.courseservice.dto.CourseResponse;
import com.raxrot.courseservice.model.Course;

public class CourseMapper {

    public static Course maptoCourse(CourseRequest courseRequest) {
        return Course.builder()
                .name(courseRequest.getName())
                .description(courseRequest.getDescription())
                .price(courseRequest.getPrice())
                .build();
    }

    public static CourseResponse maptoCourseResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .price(course.getPrice())
                .createdAt(course.getCreatedAt())
                .updatedAt(course.getUpdatedAt())
                .imageUrl(course.getImageUrl())
                .imagePublicId(course.getImagePublicId())
                .build();
    }
}
