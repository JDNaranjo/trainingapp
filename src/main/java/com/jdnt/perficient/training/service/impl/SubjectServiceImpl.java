package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.DTO.SubjectDTO;
import com.jdnt.perficient.training.exception.UserNotCreatedException;
import com.jdnt.perficient.training.exception.UserNotDeletedException;
import com.jdnt.perficient.training.exception.UserNotFoundException;
import com.jdnt.perficient.training.exception.UserNotUpdatedException;
import com.jdnt.perficient.training.entity.Subject;
import com.jdnt.perficient.training.repository.SubjectRepository;
import com.jdnt.perficient.training.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    public SubjectDTO convertSubjectToDTO(Subject subject) {

        SubjectDTO subjectDTO = new SubjectDTO();
        subjectDTO.setName(subject.getName());
        subjectDTO.setDescription(subject.getDescription());
        subjectDTO.setCourseName(subject.getCourse().getName());
        subjectDTO.setTeacherName(subject.getTeacher().getName());

        return subjectDTO;
    }

    public List<SubjectDTO> getSubjects(){
        return subjectRepository.findAll().stream()
                .map(this::convertSubjectToDTO)
                .collect(Collectors.toList());
    }

    public SubjectDTO getSubjectById(Long id){
        return convertSubjectToDTO(
                subjectRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id))
        );
    }

    public SubjectDTO createSubject(Subject newSubject){
        if (newSubject != null){
            return convertSubjectToDTO(
                    subjectRepository.save(newSubject)
            );
        }else {
            throw new UserNotCreatedException();
        }
    }

    public SubjectDTO updateSubject(Long id, Subject newSubject) {
        if (subjectRepository.existsById(id) && newSubject != null){
            Subject subject = subjectRepository.findById(id).get();

            subject.setName(newSubject.getName());
            subject.setDescription(newSubject.getDescription());
            subject.setTeacher(newSubject.getTeacher());

            return convertSubjectToDTO(
                    subjectRepository.save(subject)
                );
        }else{
            throw new UserNotUpdatedException(id);
        }
    }

    public String deleteCourse(Long id) {
        if(subjectRepository.existsById(id)){
            subjectRepository.deleteById(id);
            return "Subject deleted";
        }
        throw new UserNotDeletedException(id);
    }

}
