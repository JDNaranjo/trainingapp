package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.DTO.CourseDTO;
import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.entity.User;
import com.jdnt.perficient.training.repository.CourseRepository;
import com.jdnt.perficient.training.repository.StudentRepository;
import com.jdnt.perficient.training.repository.SubjectRepository;
import com.jdnt.perficient.training.service.CourseService;
import com.jdnt.perficient.training.service.SubjectService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.AssertTrue;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private SubjectService subjectService;

    @Mock
    private CourseService courseService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void convertCourseToDTOTest() {

        Course course = new Course();
        course.setName("Ing Sis");

        Student student = new Student();
        student.setName("Juan");
        student.setEmail("jdnt@gmail.com");
        student.setLastName("Naranjo");
        student.setUsername("jdnaranjo");
        student.setPassword("12345");

        course.setStudentsEnrolled(new HashSet<>());
        course.getStudentsEnrolled().add(student);

        CourseDTO courseDTOconverted = courseService.convertCourseToDTO(course);

        CourseDTO courseDTOexpected = new CourseDTO();
        courseDTOexpected.setName("Ing Sis");
        courseDTOexpected.setSubjectsNames(new HashSet<>());
        courseDTOexpected.getStudentsNames().add("Juan");

        assertEquals(courseDTOconverted, courseDTOexpected);
    }

    @Test
    void getCourses() {
    }

    @Test
    void getCourseById() {
    }

    @Test
    void createCourse() {
    }

    @Test
    void updateCourse() {
    }

    @Test
    void deleteCourse() {
    }

    @Test
    void enrollUser() {
    }

    @Test
    void assignSubject() {
    }
}