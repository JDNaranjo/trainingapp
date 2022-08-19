package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.dto.SubjectDTO;
import com.jdnt.perficient.training.exception.NotCreatedException;
import com.jdnt.perficient.training.exception.NotDeletedException;
import com.jdnt.perficient.training.exception.NotFoundException;
import com.jdnt.perficient.training.exception.NotUpdatedException;
import com.jdnt.perficient.training.entity.Subject;
import com.jdnt.perficient.training.repository.SubjectRepository;
import com.jdnt.perficient.training.service.SubjectService;
import com.jdnt.perficient.training.utility.MapperToDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.jdnt.perficient.training.utility.MapperToDto.convertSubjectToDTO;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    public List<SubjectDTO> getSubjects() {
        return subjectRepository.findAll().stream()
                .map(MapperToDto::convertSubjectToDTO)
                .toList();
    }

    public SubjectDTO getSubjectById(Long id) {
        return convertSubjectToDTO(
                subjectRepository.findById(id).orElseThrow(
                        () -> new NotFoundException("Subject: " + id + " not found")
                )
        );
    }

    public SubjectDTO createSubject(Subject newSubject) {
        if (newSubject != null) {
            return convertSubjectToDTO(
                    subjectRepository.save(newSubject)
            );
        }
        throw new NotCreatedException("Subject can not be null");
    }

    public SubjectDTO updateSubject(Long id, Subject newSubject) {
        if (newSubject != null) {
            Subject subject = subjectRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Subject: " + id + " cant be found"));

            subject.setName(newSubject.getName());
            subject.setDescription(newSubject.getDescription());
            subject.setTeacher(newSubject.getTeacher());

            return convertSubjectToDTO(
                    subjectRepository.save(subject)
            );
        }
        throw new NotUpdatedException("Course: " + id + "is null and didn't update");
    }

    public String deleteCourse(Long id) {
        if (subjectRepository.existsById(id)) {
            subjectRepository.deleteById(id);
            return "Subject deleted";
        }
        throw new NotDeletedException("Course: " + id + " not found to be deleted");
    }

}
