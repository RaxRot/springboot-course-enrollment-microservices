package com.raxrot.courseservice.repository;

import com.raxrot.courseservice.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByName(String name);
    List<Course> findByNameContainingIgnoreCase(String keyword);
}
