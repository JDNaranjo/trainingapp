package com.jdnt.perficient.training.service;

import com.jdnt.perficient.training.entity.Subject;
import com.jdnt.perficient.training.entity.Teacher;

import java.util.List;

public interface TeacherService {

    public List<Subject> getSubjects(Long id);

    public String deleteSubject(Long teacherId, Long subjectId);

    public List<Teacher> getTeachers();

    public Teacher getTeacherById(Long id);

    public Teacher createTeacher(Teacher newUser);

    public Teacher updateTeacher(Long id, Teacher newUser);

    public String deleteTeacher(Long id);

}
