package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.DTO.SubjectDTO;
import com.jdnt.perficient.training.DTO.TeacherDTO;
import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.Student;
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

import java.util.ArrayList;
import java.util.HashSet;
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

        if(teacher.getSubjects()!=null)
            teacherDTO.setSubjectNames(teacher.getSubjects()
                    .stream().map(
                            Subject::getName
                    ).collect(Collectors.toList()));

        return teacherDTO;
    }

    public SubjectDTO updateSubject(Long teacherId, Long subjectId) {
        if(teacherRepository.existsById(teacherId) && subjectRepository.existsById(subjectId) ) {
            Teacher teacher = teacherRepository.findById(teacherId).get();
            Subject subject = subjectRepository.findById(subjectId).get();

            subject.setTeacher(teacher);

            if(teacher.getSubjects()!= null){
                teacher.getSubjects().add(subject);
            }else {
                teacher.setSubjects(new ArrayList<Subject>());
                teacher.getSubjects().add(subject);
            }

            teacherRepository.save(teacher);
            subjectRepository.save(subject);

            return subjectService.convertSubjectToDTO(subject);
        }
        throw new UserNotUpdatedException(teacherId);
    }

    public List<SubjectDTO> getSubjects(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id))
                .getSubjects()
                .stream().map(subjectService::convertSubjectToDTO)
                .collect(Collectors.toList());
    }

    public String deleteSubject(Long teacherId, Long subjectId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new UserNotFoundException(teacherId));

        teacher.getSubjects().remove(
                subjectRepository.findById(subjectId).orElseThrow(
                        () -> new UserNotFoundException(subjectId)
                ));
        return "Subject deleted";
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
        throw new UserNotCreatedException("Teacher can not be null");
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