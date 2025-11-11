package com.raxrot.courseservice.service;

import com.raxrot.courseservice.dto.CourseRequest;
import com.raxrot.courseservice.dto.CourseResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {
    CourseResponse createCourse(CourseRequest courseRequest);
    CourseResponse getCourseById(Long id);
    List<CourseResponse> getAllCourses();
    List<CourseResponse> searchCourses(String keyword);
    CourseResponse updateCourse(Long id,CourseRequest courseRequest);
    void deleteCourse(Long id);
    CourseResponse uploadCourseImage(Long id, MultipartFile file);
}
