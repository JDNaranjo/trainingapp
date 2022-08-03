package com.jdnt.perficient.training.service;

import com.jdnt.perficient.training.exception.UserNotCreatedException;
import com.jdnt.perficient.training.exception.UserNotDeletedException;
import com.jdnt.perficient.training.exception.UserNotFoundException;
import com.jdnt.perficient.training.exception.UserNotUpdatedException;
import com.jdnt.perficient.training.entity.Subject;
import com.jdnt.perficient.training.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService{

    @Autowired
    SubjectRepository subjectRepository;

    public List<Subject> getSubjects(){
        return subjectRepository.findAll();
    }

    public Subject getSubjectById(Long id){
        return subjectRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public Subject createSubject(Subject newSubject){
        if (newSubject != null){
            return subjectRepository.save(newSubject);
        }else {
            throw new UserNotCreatedException();
        }
    }

    public Subject updateSubject(Long id, Subject newSubject) {
        if (subjectRepository.existsById(id) && newSubject != null){
            Subject subject = subjectRepository.findById(id).get();

            subject.setName(newSubject.getName());
            subject.setDescription(newSubject.getDescription());
            subject.setTeacher(newSubject.getTeacher());

            return subjectRepository.save(subject);
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
