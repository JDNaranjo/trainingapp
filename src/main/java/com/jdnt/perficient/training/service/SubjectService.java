package com.jdnt.perficient.training.service;

import com.jdnt.perficient.training.entity.Subject;

import java.util.List;

public interface SubjectService {

    public List<Subject> getSubjects();
    public Subject getSubjectById(Long id);
    public Subject createSubject(Subject newSubject);
    public Subject updateSubject(Long id, Subject newSubject);
    public String deleteCourse(Long id);
}
