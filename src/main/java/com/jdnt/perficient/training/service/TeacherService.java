package com.jdnt.perficient.training.service;

import com.jdnt.perficient.training.DTO.SubjectDTO;
import com.jdnt.perficient.training.DTO.TeacherDTO;
import com.jdnt.perficient.training.entity.Teacher;

import java.util.List;

public interface TeacherService {

    public List<SubjectDTO> getSubjects(Long id);

    public String deleteSubject(Long teacherId, Long subjectId);

    public List<TeacherDTO> getTeachers();

    public TeacherDTO getTeacherById(Long id);

    public TeacherDTO createTeacher(Teacher newUser);

    public TeacherDTO updateTeacher(Long id, Teacher newUser);

    public String deleteTeacher(Long id);

}
