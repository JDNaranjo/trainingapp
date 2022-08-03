package com.jdnt.perficient.training.service;

import com.jdnt.perficient.training.entity.Course;

import java.util.List;

public interface CourseService {

    public List<Course> getCourses();
    public Course getCourseById(Long id);
    public Course createCourse(Course newCourse);
    public Course updateCourse(Long id, Course newCourse);
    public String deleteCourse(Long id);
    public Course enrollUser(Long userId, Long courseId);
}
