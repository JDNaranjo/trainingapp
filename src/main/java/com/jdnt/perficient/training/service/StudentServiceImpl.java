package com.jdnt.perficient.training.service;

import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    public Course getCourse() {
        return null;
    }

    public Course updateCourse(Long id, Course newCourse) {
        return null;
    }

    public String deleteCourse(Long id) {
        return null;
    }
}