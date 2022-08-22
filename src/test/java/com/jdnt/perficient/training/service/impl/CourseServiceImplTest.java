package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.dto.CourseDTO;
import com.jdnt.perficient.training.dto.SubjectDTO;
import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.entity.Subject;
import com.jdnt.perficient.training.exception.NotCreatedException;
import com.jdnt.perficient.training.exception.NotDeletedException;
import com.jdnt.perficient.training.exception.NotFoundException;
import com.jdnt.perficient.training.exception.NotUpdatedException;
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

import java.util.*;

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
    Course courseBlank;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setName("Ing Sis");

        courseBlank = new Course();

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
    void getCoursesEmptyTest() {
        List<CourseDTO> expectedList = new ArrayList<>();

        when(courseRepository.findAll()).thenReturn(new ArrayList<>());

        List<CourseDTO> actualList = courseService.getCourses();
        assertEquals(expectedList, actualList);
    }

    @Test
    void getCourseByIdTest() {
        when(courseRepository.findById(anyLong()))
                .thenReturn(Optional.of(course));

        CourseDTO convertedCourse = courseService.getCourseById(123L);

        CourseDTO expectedCourse = MapperToDto.convertCourseToDTO(course);

        assertEquals(expectedCourse, convertedCourse);
    }

    @Test
    void getCourseByIdNotFoundTest() {
        when(courseRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> courseService.getCourseById(123L));
    }

    @Test
    void createCourseTest() {
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        CourseDTO convertedCourse = courseService.createCourse(course);

        CourseDTO expectedCourse = new CourseDTO();
        expectedCourse.setName("Ing Sis");

        assertEquals(expectedCourse, convertedCourse);
    }

    @Test
    void createCourseNullTest() {
        assertThrows(NotCreatedException.class,
                () -> courseService.createCourse(null));
    }

    @Test
    void createCourseBlankTest() {
        when(courseRepository.save(any(Course.class))).thenReturn(courseBlank);

        CourseDTO convertedCourse = courseService.createCourse(courseBlank);

        CourseDTO expectedCourse = new CourseDTO();

        assertEquals(expectedCourse, convertedCourse);
    }

    @Test
    void updateCourseTest() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(courseRepository.save(any(Course.class))).thenReturn(course2);

        CourseDTO convertedCourse = courseService.updateCourse(123L, course2);

        CourseDTO expectedCourse = MapperToDto.convertCourseToDTO(course2);

        assertEquals(convertedCourse, expectedCourse);
    }

    @Test
    void updateCourseNullTest() {
        assertThrows(NotUpdatedException.class,
                () -> courseService.updateCourse(123L, null));
    }

    @Test
    void updateCourseNotFoundIdTest() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotUpdatedException.class,
                () -> courseService.updateCourse(123L, course2));
    }

    @Test
    void deleteCourseTest() {
        when(courseRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(courseRepository).deleteById(anyLong());

        Map<String, String> body = new HashMap<>();
        body.put("result", "Course deleted");

        assertEquals(body, courseService.deleteCourse(123L));
    }

    @Test
    void deleteCourseNotFoundTest() {
        when(courseRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NotDeletedException.class,
                () -> courseService.deleteCourse(123L));
    }

    @Test
    void enrollUserTest() {
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
    void enrollUserNotFoundStudentTest() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> courseService.enrollUser(123L, 321L)
        );
    }

    @Test
    void enrollUserNotFoundCourseTest() {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> courseService.enrollUser(123L, 321L)
        );
    }

    @Test
    void assignSubjectTest() {
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));

        SubjectDTO convertedSubject = courseService.assignSubject(123L, 321L);

        SubjectDTO expectedSubject = new SubjectDTO();
        expectedSubject.setName("Ing Soft");
        expectedSubject.setDescription("Ing Soft Desc");
        expectedSubject.setCourseName("Ing Sis");

        assertEquals(convertedSubject, expectedSubject);
    }

    @Test
    void assignSubjectNotFoundCourseTest() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> courseService.assignSubject(123L, 321L)
        );
    }

    @Test
    void assignSubjectNotFoundSubjectTest() {
        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> courseService.assignSubject(123L, 321L)
        );
    }
}