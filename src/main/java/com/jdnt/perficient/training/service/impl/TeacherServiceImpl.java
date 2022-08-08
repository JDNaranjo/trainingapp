package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.DTO.SubjectDTO;
import com.jdnt.perficient.training.DTO.TeacherDTO;
import com.jdnt.perficient.training.entity.Subject;
import com.jdnt.perficient.training.entity.Teacher;
import com.jdnt.perficient.training.exception.UserNotCreatedException;
import com.jdnt.perficient.training.exception.UserNotDeletedException;
import com.jdnt.perficient.training.exception.UserNotFoundException;
import com.jdnt.perficient.training.exception.UserNotUpdatedException;
import com.jdnt.perficient.training.repository.SubjectRepository;
import com.jdnt.perficient.training.repository.TeacherRepository;
import com.jdnt.perficient.training.service.SubjectService;
import com.jdnt.perficient.training.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    SubjectRepository subjectRepository;

    @Autowired
    SubjectService subjectService;

    public TeacherDTO convertTeacherToDTO(Teacher teacher) {

        TeacherDTO teacherDTO = new TeacherDTO();
        teacherDTO.setName(teacher.getName());
        teacherDTO.setLastName(teacher.getLastName());
        teacherDTO.setUsername(teacher.getUsername());
        teacherDTO.setEmail(teacher.getEmail());

        teacherDTO.setSubjectNames(teacher.getSubjects()
                .stream().map(
                        Subject::getName
                ).collect(Collectors.toList()));

        return teacherDTO;
    }

    public List<SubjectDTO> getSubjects(Long id) {
        if(teacherRepository.existsById(id)){
            return teacherRepository.findById(id).get().getSubjects()
                    .stream().map(subjectService::convertSubjectToDTO)
                    .collect(Collectors.toList());
        }
        throw new UserNotFoundException(id);
    }

    public String deleteSubject(Long teacherId, Long subjectId) {
        if(teacherRepository.existsById(teacherId)){
            Teacher teacher = teacherRepository.findById(teacherId).get();
            teacher.getSubjects().remove(
                    subjectRepository.findById(subjectId).orElseThrow(
                            () -> new UserNotFoundException(subjectId)
                    ));
            return "Subject deleted";
        }
        throw new UserNotFoundException(subjectId);
    }

    public List<TeacherDTO> getTeachers() {
        return teacherRepository.findAll()
                .stream().map(this::convertTeacherToDTO)
                .collect(Collectors.toList());
    }

    public TeacherDTO getTeacherById(Long id) {
        return convertTeacherToDTO(
                teacherRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException(id))
        );
    }

    public TeacherDTO createTeacher(Teacher newUser) {
        if(newUser!=null){
            return convertTeacherToDTO(
                    teacherRepository.save(newUser)
            );
        }
        throw new UserNotCreatedException();
    }

    public TeacherDTO updateTeacher(Long id, Teacher newUser) {
        if (teacherRepository.existsById(id) && newUser != null){
            Teacher user = teacherRepository.findById(id).get();

            user.setEmail(newUser.getEmail());
            user.setLastName(newUser.getLastName());
            user.setName(newUser.getName());
            user.setPassword(newUser.getPassword());
            user.setUsername(newUser.getUsername());
            user.setSubjects(newUser.getSubjects());

            return convertTeacherToDTO(
                    teacherRepository.save(user)
            );
        }
        throw new UserNotUpdatedException(id);
    }

    public String deleteTeacher(Long id) {
        if(teacherRepository.existsById(id)){
            teacherRepository.deleteById(id);
            return "User deleted";
        }
        throw new UserNotDeletedException(id);
    }
}