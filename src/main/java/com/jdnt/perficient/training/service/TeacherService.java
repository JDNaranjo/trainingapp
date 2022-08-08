package com.jdnt.perficient.training.service;

import com.jdnt.perficient.training.DTO.SubjectDTO;
import com.jdnt.perficient.training.DTO.TeacherDTO;
import com.jdnt.perficient.training.entity.Teacher;

import java.util.List;

public interface TeacherService {

    SubjectDTO updateSubject(Long teacherId, Long subjectId);

    List<SubjectDTO> getSubjects(Long id);

    String deleteSubject(Long teacherId, Long subjectId);

    List<TeacherDTO> getTeachers();

    TeacherDTO getTeacherById(Long id);

    TeacherDTO createTeacher(Teacher newUser);

    TeacherDTO updateTeacher(Long id, Teacher newUser);

    String deleteTeacher(Long id);

}
