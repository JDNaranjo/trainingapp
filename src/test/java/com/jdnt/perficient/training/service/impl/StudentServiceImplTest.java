package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.dto.CourseDTO;
import com.jdnt.perficient.training.dto.StudentDTO;
import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.exception.NotCreatedException;
import com.jdnt.perficient.training.exception.NotDeletedException;
import com.jdnt.perficient.training.exception.NotFoundException;
import com.jdnt.perficient.training.exception.NotUpdatedException;
import com.jdnt.perficient.training.repository.CourseRepository;
import com.jdnt.perficient.training.repository.StudentRepository;
import com.jdnt.perficient.training.repository.UserRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    StudentServiceImpl studentService = new StudentServiceImpl();

    Student student;
    Student student2;
    Student studentBlank;
    Course course;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setName("Juan");
        student.setLastName("Naranjo");
        student.setEmail("jn@gmail.com");
        student.setUsername("JuanNaranjo");
        student.setPassword("12345");

        student2 = new Student();
        student2.setName("Diego");
        student2.setLastName("Tafur");
        student2.setEmail("dt@gmail.com");
        student2.setUsername("DiegoTafur");
        student2.setPassword("54321");

        studentBlank = new Student();

        course = new Course();
        course.setName("UX");
    }

    @Test
    void getStudentsTest() {
        List<Student> studentList = new ArrayList<>();
        studentList.add(student);
        studentList.add(student2);

        when(studentRepository.findAll()).thenReturn(studentList);

        List<StudentDTO> actualList = studentService.getStudents();

        List<StudentDTO> expectedList = new ArrayList<>();
        expectedList.add(MapperToDto.convertStudentToDTO(student));
        expectedList.add(MapperToDto.convertStudentToDTO(student2));

        assertEquals(actualList, expectedList);
    }

    @Test
    void getStudentsEmptyTest() {
        List<Student> studentList = new ArrayList<>();

        when(studentRepository.findAll()).thenReturn(studentList);

        List<StudentDTO> actualList = studentService.getStudents();

        List<StudentDTO> expectedList = new ArrayList<>();

        assertEquals(actualList, expectedList);
    }

    @Test
    void getStudentByIdTest() {
        when(studentRepository.findById(anyLong()))
                .thenReturn(Optional.of(student));

        StudentDTO convertStudent = studentService.getStudentById(123L);

        StudentDTO expectedStudent = MapperToDto.convertStudentToDTO(student);

        assertEquals(convertStudent, expectedStudent);
    }

    @Test
    void getStudentByIdNotFoundTest() {
        when(studentRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> studentService.getStudentById(123L)
        );
    }

    @Test
    void createStudentTest() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentDTO convertStudent = studentService.createStudent(student);

        StudentDTO expectedStudent = new StudentDTO();
        expectedStudent.setName("Juan");
        expectedStudent.setLastName("Naranjo");
        expectedStudent.setEmail("jn@gmail.com");
        expectedStudent.setUsername("JuanNaranjo");

        assertEquals(convertStudent, expectedStudent);
    }

    @Test
    void createStudentNullTest() {
        assertThrows(NotCreatedException.class,
                () -> studentService.createStudent(null));
    }

    @Test
    void createStudentBlankTest() {
        when(studentRepository.save(any(Student.class))).thenReturn(studentBlank);

        StudentDTO convertStudent = studentService.createStudent(studentBlank);

        StudentDTO expectedStudent = new StudentDTO();

        assertEquals(convertStudent, expectedStudent);
    }

    @Test
    void updateStudentTest() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student2);

        StudentDTO convertStudent = studentService.updateStudent(123L, student2);

        StudentDTO expectedStudent = MapperToDto.convertStudentToDTO(student2);

        assertEquals(convertStudent, expectedStudent);
    }

    @Test
    void updateStudentNotFoundTest() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> studentService.updateStudent(123L, student2)
        );
    }

    @Test
    void updateStudentNullTest() {
        assertThrows(NotUpdatedException.class,
                () -> studentService.updateStudent(123L, null)
        );
    }

    @Test
    void deleteStudentTest() {
        when(studentRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(studentRepository).deleteById(anyLong());

        assertEquals("User deleted", studentService.deleteStudent(123L));
    }

    @Test
    void deleteStudentNotFoundTest() {
        when(studentRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NotDeletedException.class,
                () -> studentService.deleteStudent(123L)
        );
    }

    @Test
    void getCourseTest() {
        student.setCourse(course);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        CourseDTO expectedCourse = new CourseDTO();
        expectedCourse.setName("UX");

        CourseDTO actualCourse = studentService.getCourse(123L);

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    void getCourseNullTest() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        assertThrows(NotFoundException.class,
                () -> studentService.getCourse(123L)
        );
    }

    @Test
    void getCourseNotFoundTest() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> studentService.getCourse(123L)
        );
    }

    @Test
    void updateCourseTest() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        CourseDTO convertedCourse = studentService.updateCourse(123L, 321L);

        CourseDTO expectedCourse = new CourseDTO();
        expectedCourse.setName("UX");
        expectedCourse.setStudentsNames(new HashSet<>());
        expectedCourse.getStudentsNames().add("Juan");

        assertEquals(convertedCourse, expectedCourse);
    }

    @Test
    void updateCourseWithMoreStudentsTest() {
        course.setStudentsEnrolled(new HashSet<>());
        course.getStudentsEnrolled().add(student2);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        CourseDTO convertedCourse = studentService.updateCourse(123L, 321L);

        CourseDTO expectedCourse = new CourseDTO();
        expectedCourse.setName("UX");
        expectedCourse.setStudentsNames(new HashSet<>());
        expectedCourse.getStudentsNames().add("Diego");
        expectedCourse.getStudentsNames().add("Juan");

        assertEquals(convertedCourse, expectedCourse);
    }

    @Test
    void updateCourseNotFoundStudentTest() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> studentService.updateCourse(123L, 321L)
        );
    }

    @Test
    void updateCourseNotFoundCourseTest() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> studentService.updateCourse(123L, 321L)
        );
    }

    @Test
    void deleteCourseTest() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        assertEquals("User deleted", studentService.deleteCourse(123L));
    }

    @Test
    void deleteCourseNoyFoundTest() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> studentService.deleteCourse(123L)
        );
    }
}