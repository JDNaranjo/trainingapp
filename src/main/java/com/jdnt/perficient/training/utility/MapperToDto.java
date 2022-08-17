package com.jdnt.perficient.training.utility;

import com.jdnt.perficient.training.dto.CourseDTO;
import com.jdnt.perficient.training.dto.StudentDTO;
import com.jdnt.perficient.training.dto.SubjectDTO;
import com.jdnt.perficient.training.dto.TeacherDTO;
import com.jdnt.perficient.training.entity.*;
import com.jdnt.perficient.training.exception.NotFoundException;

import java.util.stream.Collectors;

public class MapperToDto {

    private MapperToDto(){}

    public static CourseDTO convertCourseToDTO(Course course) {
        if (course != null){
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setName(course.getName());

            if (course.getStudentsEnrolled()!=null)
                courseDTO.setStudentsNames(course.getStudentsEnrolled()
                        .stream().map(
                                User::getName
                        ).collect(Collectors.toSet()));

            if(course.getSubjects()!=null)
                courseDTO.setSubjectsNames(course.getSubjects()
                        .stream().map(
                                Subject::getName
                        ).collect(Collectors.toSet()));

            return courseDTO;
        }
        throw new NotFoundException("Course is null");
    }

    public static StudentDTO convertStudentToDTO(Student student) {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(student.getName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setUsername(student.getUsername());
        studentDTO.setEmail(student.getEmail());

        if (student.getCourse()!=null)
            studentDTO.setCourseName(student.getCourse().getName());

        return studentDTO;
    }

    public static SubjectDTO convertSubjectToDTO(Subject subject) {

        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName(subject.getName());
        subjectDTO.setDescription(subject.getDescription());

        if(subject.getCourse()!=null)
            subjectDTO.setCourseName(subject.getCourse().getName());

        if(subject.getTeacher()!=null)
            subjectDTO.setTeacherName(subject.getTeacher().getName());

        return subjectDTO;
    }

    public static TeacherDTO convertTeacherToDTO(Teacher teacher) {

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setName(teacher.getName());
        teacherDTO.setLastName(teacher.getLastName());
        teacherDTO.setUsername(teacher.getUsername());
        teacherDTO.setEmail(teacher.getEmail());

        if(teacher.getSubjects()!=null)
            teacherDTO.setSubjectNames(teacher.getSubjects()
                    .stream().map(
                            Subject::getName
                    ).toList());

        return teacherDTO;
    }
}
