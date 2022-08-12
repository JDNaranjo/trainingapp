package com.jdnt.perficient.training.service;

import com.jdnt.perficient.training.dto.CourseDTO;
import com.jdnt.perficient.training.dto.StudentDTO;
import com.jdnt.perficient.training.entity.Student;

import java.util.List;

public interface StudentService {

    public CourseDTO getCourse(Long id);

    public String deleteCourse(Long id);

    public CourseDTO updateCourse(Long studentId, Long courseId);

    public List<StudentDTO> getStudents();

    public StudentDTO getStudentById(Long id);

    public StudentDTO createStudent(Student newUser);

    public StudentDTO updateStudent(Long id, Student newUser);

    public String deleteStudent(Long id);
}
