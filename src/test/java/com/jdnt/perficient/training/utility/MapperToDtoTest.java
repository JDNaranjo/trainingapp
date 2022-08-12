package com.jdnt.perficient.training.utility;

import com.jdnt.perficient.training.dto.CourseDTO;
import com.jdnt.perficient.training.dto.StudentDTO;
import com.jdnt.perficient.training.dto.SubjectDTO;
import com.jdnt.perficient.training.dto.TeacherDTO;
import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.entity.Subject;
import com.jdnt.perficient.training.entity.Teacher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class MapperToDtoTest {

    Course course;
    Student student;
    Student student2;
    Subject subject;
    Subject subject2;
    Teacher teacher;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setName("Ing Sis");

        student = new Student();
        student.setName("Juan");
        student.setLastName("Naranjo");
        student.setUsername("jdnaranjo");
        student.setEmail("jdnt@gmail.com");

        student2 = new Student();
        student2.setName("Felipe");
        student2.setLastName("Sanchez");
        student2.setUsername("avalanche");
        student2.setEmail("lfsanchez@hotmail.com");

        subject = new Subject();
        subject.setName("Ing Soft");
        subject.setDescription("Ing Soft Desc");

        subject2 = new Subject();
        subject2.setName("IoT");

        teacher = new Teacher();
        teacher.setName("Carlos");
        teacher.setLastName("Diaz");
        teacher.setUsername("cardiac");
        teacher.setEmail("cadiaz@hotmail.com");
    }

    @Test
    void convertCourseToDTOWithStudentsTest() {

        course.setStudentsEnrolled(new HashSet<>());
        course.getStudentsEnrolled().add(student);
        course.getStudentsEnrolled().add(student2);

        CourseDTO courseDTOConverted = MapperToDto.convertCourseToDTO(course);

        CourseDTO courseDTOExpected = new CourseDTO();
        courseDTOExpected.setName("Ing Sis");
        courseDTOExpected.setStudentsNames(new HashSet<>());
        courseDTOExpected.getStudentsNames().add("Juan");
        courseDTOExpected.getStudentsNames().add("Felipe");

        assertEquals(courseDTOConverted, courseDTOExpected);
    }

    @Test
    void convertCourseToDTOWithSubjectsTest() {

        course.setSubjects(new HashSet<>());
        course.getSubjects().add(subject);
        course.getSubjects().add(subject2);

        CourseDTO courseDTOConverted = MapperToDto.convertCourseToDTO(course);

        CourseDTO courseDTOExpected = new CourseDTO();
        courseDTOExpected.setName("Ing Sis");
        courseDTOExpected.setSubjectsNames(new HashSet<>());
        courseDTOExpected.getSubjectsNames().add("Ing Soft");
        courseDTOExpected.getSubjectsNames().add("IoT");

        assertEquals(courseDTOConverted, courseDTOExpected);
    }

    @Test
    void convertCourseToDTOWithSubjectsAndStudentsTest() {

        course.setSubjects(new HashSet<>());
        course.getSubjects().add(subject);
        course.getSubjects().add(subject2);

        course.setStudentsEnrolled(new HashSet<>());
        course.getStudentsEnrolled().add(student);
        course.getStudentsEnrolled().add(student2);

        CourseDTO courseDTOConverted = MapperToDto.convertCourseToDTO(course);

        CourseDTO courseDTOExpected = new CourseDTO();
        courseDTOExpected.setName("Ing Sis");
        courseDTOExpected.setSubjectsNames(new HashSet<>());
        courseDTOExpected.getSubjectsNames().add("Ing Soft");
        courseDTOExpected.getSubjectsNames().add("IoT");

        courseDTOExpected.setStudentsNames(new HashSet<>());
        courseDTOExpected.getStudentsNames().add("Juan");
        courseDTOExpected.getStudentsNames().add("Felipe");

        assertEquals(courseDTOConverted, courseDTOExpected);
    }

    @Test
    void convertCourseToDTOWithNoRelationsTest() {

        CourseDTO courseDTOConverted = MapperToDto.convertCourseToDTO(course);

        CourseDTO courseDTOExpected = new CourseDTO();
        courseDTOExpected.setName("Ing Sis");

        assertEquals(courseDTOConverted, courseDTOExpected);
    }

    @Test
    void convertStudentToDTOWithCourseTest() {
        student.setCourse(course);

        StudentDTO studentDTOConverted = MapperToDto.convertStudentToDTO(student);

        StudentDTO studentDTOExpected = new StudentDTO();
        studentDTOExpected.setUsername("jdnaranjo");
        studentDTOExpected.setName("Juan");
        studentDTOExpected.setLastName("Naranjo");
        studentDTOExpected.setEmail("jdnt@gmail.com");
        studentDTOExpected.setCourseName("Ing Sis");

        assertEquals(studentDTOConverted, studentDTOExpected);
    }

    @Test
    void convertStudentToDTOWithNoCourseTest() {

        StudentDTO studentDTOConverted = MapperToDto.convertStudentToDTO(student);

        StudentDTO studentDTOExpected = new StudentDTO();
        studentDTOExpected.setUsername("jdnaranjo");
        studentDTOExpected.setName("Juan");
        studentDTOExpected.setLastName("Naranjo");
        studentDTOExpected.setEmail("jdnt@gmail.com");

        assertEquals(studentDTOConverted, studentDTOExpected);
    }

    @Test
    void convertSubjectToDTOWithCourseTest() {
        subject.setCourse(course);

        SubjectDTO subjectDTOConverted = MapperToDto.convertSubjectToDTO(subject);

        SubjectDTO subjectDTOExpected = new SubjectDTO();
        subjectDTOExpected.setName("Ing Soft");
        subjectDTOExpected.setDescription("Ing Soft Desc");
        subjectDTOExpected.setCourseName("Ing Sis");

        assertEquals(subjectDTOExpected, subjectDTOConverted);
    }

    @Test
    void convertSubjectToDTOWithTeacherTest() {
        subject.setTeacher(teacher);

        SubjectDTO subjectDTOConverted = MapperToDto.convertSubjectToDTO(subject);

        SubjectDTO subjectDTOExpected = new SubjectDTO();
        subjectDTOExpected.setName("Ing Soft");
        subjectDTOExpected.setDescription("Ing Soft Desc");
        subjectDTOExpected.setTeacherName("Carlos");

        assertEquals(subjectDTOExpected, subjectDTOConverted);
    }

    @Test
    void convertSubjectToDTOWithTeacherAndCourseTest() {
        subject.setTeacher(teacher);
        subject.setCourse(course);

        SubjectDTO subjectDTOConverted = MapperToDto.convertSubjectToDTO(subject);

        SubjectDTO subjectDTOExpected = new SubjectDTO();
        subjectDTOExpected.setName("Ing Soft");
        subjectDTOExpected.setDescription("Ing Soft Desc");
        subjectDTOExpected.setTeacherName("Carlos");
        subjectDTOExpected.setCourseName("Ing Sis");

        assertEquals(subjectDTOExpected, subjectDTOConverted);
    }

    @Test
    void convertSubjectToDTOWithNoRelationsTest() {
        SubjectDTO subjectDTOConverted = MapperToDto.convertSubjectToDTO(subject);

        SubjectDTO subjectDTOExpected = new SubjectDTO();
        subjectDTOExpected.setName("Ing Soft");
        subjectDTOExpected.setDescription("Ing Soft Desc");

        assertEquals(subjectDTOExpected, subjectDTOConverted);
    }

    @Test
    void convertTeacherToDTOWithSubjectsTest() {
        teacher.setSubjects(new ArrayList<>());
        teacher.getSubjects().add(subject);
        teacher.getSubjects().add(subject2);

        TeacherDTO teacherDTOConverted = MapperToDto.convertTeacherToDTO(teacher);

        TeacherDTO teacherDTOExpected = new TeacherDTO();
        teacherDTOExpected.setName("Carlos");
        teacherDTOExpected.setLastName("Diaz");
        teacherDTOExpected.setUsername("cardiac");
        teacherDTOExpected.setEmail("cadiaz@hotmail.com");
        teacherDTOExpected.setSubjectNames(new ArrayList<>());
        teacherDTOExpected.getSubjectNames().add("Ing Soft");
        teacherDTOExpected.getSubjectNames().add("IoT");

        assertEquals(teacherDTOConverted, teacherDTOExpected);
    }

    @Test
    void convertTeacherToDTOWithNoRelationsTest() {
        TeacherDTO teacherDTOConverted = MapperToDto.convertTeacherToDTO(teacher);

        TeacherDTO teacherDTOExpected = new TeacherDTO();
        teacherDTOExpected.setName("Carlos");
        teacherDTOExpected.setLastName("Diaz");
        teacherDTOExpected.setUsername("cardiac");
        teacherDTOExpected.setEmail("cadiaz@hotmail.com");

        assertEquals(teacherDTOConverted, teacherDTOExpected);
    }
}