package com.raxrot.courseservice.service.impl;

import com.raxrot.courseservice.dto.CourseRequest;
import com.raxrot.courseservice.dto.CourseResponse;
import com.raxrot.courseservice.exceptions.ApiException;
import com.raxrot.courseservice.mapper.CourseMapper;
import com.raxrot.courseservice.model.Course;
import com.raxrot.courseservice.repository.CourseRepository;
import com.raxrot.courseservice.service.CourseService;
import com.raxrot.courseservice.service.ImageUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final ImageUploadService imageUploadService;

    @Override
    public CourseResponse createCourse(CourseRequest courseRequest) {
        if (courseRepository.findByName(courseRequest.getName()).isPresent()) {
            throw new ApiException("Course already exists", HttpStatus.CONFLICT);
        }
        Course course = CourseMapper.maptoCourse(courseRequest);
        Course savedCourse=courseRepository.save(course);
        return CourseMapper.maptoCourseResponse(savedCourse);
    }

    @Override
    public List<CourseResponse> getAllCourses() {
        List<Course>courses= courseRepository.findAll();
        List<CourseResponse>courseResponses=courses.stream()
                .map(course->CourseMapper.maptoCourseResponse(course))
                .collect(Collectors.toList());
        return courseResponses;
    }

    @Override
    public List<CourseResponse> searchCourses(String keyword) {
        List<Course>courses= courseRepository.findByNameContainingIgnoreCase(keyword);
        List<CourseResponse>courseResponses=courses.stream()
                .map(course -> CourseMapper.maptoCourseResponse(course))
                .collect(Collectors.toList());
        return courseResponses;
    }

    @Override
    public CourseResponse updateCourse(Long id, CourseRequest courseRequest) {
        Course course=courseRepository.findById(id)
                .orElseThrow(()->new ApiException("Course not found",HttpStatus.NOT_FOUND));
        if (!course.getName().equals(courseRequest.getName())
                && courseRepository.findByName(courseRequest.getName()).isPresent()) {
            throw new ApiException("Course already exists",HttpStatus.CONFLICT);
        }
        course.setName(courseRequest.getName());
        course.setDescription(courseRequest.getDescription());

        Course updatedCourse=courseRepository.save(course);

        return CourseMapper.maptoCourseResponse(updatedCourse);
    }

    @Override
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ApiException("Course not found",HttpStatus.NOT_FOUND);
        }
        courseRepository.deleteById(id);
    }

    @Override
    public CourseResponse uploadCourseImage(Long id, MultipartFile file) {
        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new ApiException("Course not found", HttpStatus.NOT_FOUND));

        if (c.getImagePublicId() != null) {
            imageUploadService.delete(c.getImagePublicId());
        }

        ImageUploadService.UploadResult result = imageUploadService.upload(file, "courses");
        c.setImageUrl(result.getUrl());
        c.setImagePublicId(result.getPublicId());

        Course saved = courseRepository.save(c);
        return CourseMapper.maptoCourseResponse(saved);
    }
}
