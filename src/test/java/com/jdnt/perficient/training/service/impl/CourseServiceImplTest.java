package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.dto.CourseDTO;
import com.jdnt.perficient.training.dto.SubjectDTO;
import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.entity.Subject;
import com.jdnt.perficient.training.repository.CourseRepository;
import com.jdnt.perficient.training.repository.StudentRepository;
import com.jdnt.perficient.training.repository.SubjectRepository;
import com.jdnt.perficient.training.service.CourseService;
import com.jdnt.perficient.training.utility.MapperToDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @InjectMocks
    private CourseService courseService = new CourseServiceImpl();

    Course course;
    Course course2;
    Student student;
    Subject subject;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setName("Ing Sis");

        course2 = new Course();
        course2.setName("Ing Tel");

        student = new Student();
        student.setName("Juan");
        student.setLastName("Naranjo");
        student.setUsername("jdnaranjo");
        student.setEmail("jdnt@gmail.com");

        subject = new Subject();
        subject.setName("Ing Soft");
        subject.setDescription("Ing Soft Desc");
    }

    @Test
    void getCoursesTest() {
        List<Course> coursesList = new ArrayList<>();
        coursesList.add(course);
        coursesList.add(course2);

        List<CourseDTO> expectedList = new ArrayList<>();
        expectedList.add(MapperToDto.convertCourseToDTO(course));
        expectedList.add(MapperToDto.convertCourseToDTO(course2));

        when(courseRepository.findAll()).thenReturn(coursesList);

        List<CourseDTO> actualList = courseService.getCourses();
        assertEquals(expectedList, actualList);
    }

    @Test
    void getCourseById() {
        when(courseRepository.findById(anyLong()))
                .thenReturn(Optional.of(course));

        CourseDTO convertedCourse = courseService.getCourseById(123L);

        CourseDTO expectedCourse = MapperToDto.convertCourseToDTO(course);

        assertEquals(expectedCourse, convertedCourse);
    }

    @Test
    void createCourse() {
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        CourseDTO convertedCourse = courseService.createCourse(course);

        CourseDTO expectedCourse = new CourseDTO();
        expectedCourse.setName("Ing Sis");

        assertEquals(expectedCourse, convertedCourse);
    }

    @Test
    void updateCourse() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course2);

        CourseDTO convertedCourse = courseService.updateCourse(123L, course2);

        CourseDTO expectedCourse = MapperToDto.convertCourseToDTO(course2);

        assertEquals(convertedCourse, expectedCourse);
    }

    @Test
    void deleteCourse() {
        when(courseRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(courseRepository).deleteById(anyLong());

        assertEquals("Course deleted", courseService.deleteCourse(123L));
    }

    @Test
    void enrollUser() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        CourseDTO convertedCourse = courseService.enrollUser(123L, 321L);

        CourseDTO expectedCourse = new CourseDTO();
        expectedCourse.setName("Ing Sis");
        expectedCourse.setStudentsNames(new HashSet<>());
        expectedCourse.getStudentsNames().add(student.getName());

        assertEquals(convertedCourse, expectedCourse);
    }

    @Test
    void assignSubject() {
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        SubjectDTO convertedSubject = courseService.assignSubject(123L, 321L);

        SubjectDTO expectedSubject = new SubjectDTO();
        expectedSubject.setName("Ing Soft");
        expectedSubject.setDescription("Ing Soft Desc");
        expectedSubject.setCourseName("Ing Sis");

        assertEquals(convertedSubject, expectedSubject);
    }
}