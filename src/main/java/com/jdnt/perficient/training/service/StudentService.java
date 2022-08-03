package com.jdnt.perficient.training.service;

import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.Subject;

import java.util.List;

public interface StudentService {

    public Course getCourse();
    public Course updateCourse(Long id, Course newCourse);
    public String deleteCourse(Long id);

}
