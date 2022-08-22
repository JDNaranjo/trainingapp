package com.jdnt.perficient.training.service;

import com.jdnt.perficient.training.dto.CourseDTO;
import com.jdnt.perficient.training.dto.SubjectDTO;
import com.jdnt.perficient.training.entity.Course;

import java.util.List;
import java.util.Map;

public interface CourseService {

    List<CourseDTO> getCourses();
    CourseDTO getCourseById(Long id);
    CourseDTO createCourse(Course newCourse);
    CourseDTO updateCourse(Long id, Course newCourse);
    Map<String, String> deleteCourse(Long id);
    CourseDTO enrollUser(Long userId, Long courseId);
    SubjectDTO assignSubject(Long courseId, Long subjectId);
}
