package com.jdnt.perficient.training.service;

import com.jdnt.perficient.training.DTO.SubjectDTO;
import com.jdnt.perficient.training.entity.Subject;

import java.util.List;

public interface SubjectService {

    public SubjectDTO convertSubjectToDTO(Subject subject);
    public List<SubjectDTO> getSubjects();
    public SubjectDTO getSubjectById(Long id);
    public SubjectDTO createSubject(Subject newSubject);
    public SubjectDTO updateSubject(Long id, Subject newSubject);
    public String deleteCourse(Long id);
}
