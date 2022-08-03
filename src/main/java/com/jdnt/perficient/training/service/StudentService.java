package com.jdnt.perficient.training.service;

import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.Student;

import java.util.List;

public interface StudentService {

    public Course getCourse(Long id);

    public String deleteCourse(Long id);

    public List<Student> getStudents();

    public Student getStudentById(Long id);

    public Student createStudent(Student newUser);

    public Student updateStudent(Long id, Student newUser);

    public String deleteStudent(Long id);
}
