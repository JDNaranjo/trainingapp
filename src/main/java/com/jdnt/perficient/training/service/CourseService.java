package com.jdnt.perficient.training.service;

import com.jdnt.perficient.training.DTO.CourseDTO;
import com.jdnt.perficient.training.DTO.SubjectDTO;
import com.jdnt.perficient.training.entity.Course;

import java.util.List;

public interface CourseService {

    CourseDTO convertCourseToDTO(Course course);
    List<CourseDTO> getCourses();
    CourseDTO getCourseById(Long id);
    CourseDTO createCourse(Course newCourse);
    CourseDTO updateCourse(Long id, Course newCourse);
    String deleteCourse(Long id);
    CourseDTO enrollUser(Long userId, Long courseId);
    SubjectDTO assignSubject(Long courseId, Long subjectId);
}
