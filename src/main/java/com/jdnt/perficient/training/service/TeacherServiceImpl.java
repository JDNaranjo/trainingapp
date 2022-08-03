package com.jdnt.perficient.training.service;

import com.jdnt.perficient.training.entity.Subject;
import com.jdnt.perficient.training.entity.Teacher;
import com.jdnt.perficient.training.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService{

    @Autowired
    TeacherRepository teacherRepository;

    public List<Subject> getSubjects(Long id) {
        return null;
    }

    public String deleteSubject(Long id) {
        return null;
    }

    public List<Teacher> getTeachers() {
        return null;
    }

    public Teacher getTeacherById(Long id) {
        return null;
    }

    public Teacher createTeacher(Teacher newUser) {
        return null;
    }

    public Teacher updateTeacher(Long id, Teacher newUser) {
        return null;
    }

    public String deleteTeacher(Long id) {
        return null;
    }
}
