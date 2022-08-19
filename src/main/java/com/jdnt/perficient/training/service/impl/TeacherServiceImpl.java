package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.dto.SubjectDTO;
import com.jdnt.perficient.training.dto.TeacherDTO;
import com.jdnt.perficient.training.entity.Subject;
import com.jdnt.perficient.training.entity.Teacher;
import com.jdnt.perficient.training.exception.NotCreatedException;
import com.jdnt.perficient.training.exception.NotDeletedException;
import com.jdnt.perficient.training.exception.NotFoundException;
import com.jdnt.perficient.training.exception.NotUpdatedException;
import com.jdnt.perficient.training.repository.SubjectRepository;
import com.jdnt.perficient.training.repository.TeacherRepository;
import com.jdnt.perficient.training.repository.UserRepository;
import com.jdnt.perficient.training.service.TeacherService;
import com.jdnt.perficient.training.utility.MapperToDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.jdnt.perficient.training.utility.MapperToDto.convertTeacherToDTO;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    UserRepository userRepository;

    public SubjectDTO updateSubject(Long teacherId, Long subjectId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(
                () -> new NotFoundException("Teacher: " + teacherId + " was not found")
        );
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new NotFoundException("Subject: " + subjectId + " was not found")
        );
        subject.setTeacher(teacher);

        if (teacher.getSubjects() != null) {
            teacher.getSubjects().add(subject);
        } else {
            teacher.setSubjects(new ArrayList<>());
            teacher.getSubjects().add(subject);
        }
        teacherRepository.save(teacher);
        subjectRepository.save(subject);

        return MapperToDto.convertSubjectToDTO(subject);
    }

    public List<SubjectDTO> getSubjects(Long id) {
        return teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Teacher: " + id + " not found"))
                .getSubjects()
                .stream().map(MapperToDto::convertSubjectToDTO)
                .toList();
    }

    public String deleteSubject(Long teacherId, Long subjectId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new NotFoundException("Teacher: " + teacherId + " not found"));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new NotFoundException("Subject: " + subjectId + " not found")
        );
        try {
            teacher.getSubjects().remove(subject);
        } catch (Exception e) {
            throw new NotDeletedException("Subject not associate to teacher");
        }
        return "Subject deleted";
    }

    public List<TeacherDTO> getTeachers() {
        return teacherRepository.findAll()
                .stream().map(MapperToDto::convertTeacherToDTO)
                .toList();
    }

    public TeacherDTO getTeacherById(Long id) {
        return convertTeacherToDTO(
                teacherRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Teacher: " + id + " not found"))
        );
    }

    public TeacherDTO createTeacher(Teacher newUser) {
        if (newUser != null) {
            long repeated = userRepository.findAll().stream()
                    .filter(user -> user.getEmail().equals(newUser.getEmail())).count();
            if (repeated == 0L) {
                return convertTeacherToDTO(
                        teacherRepository.save(newUser)
                );
            }
            throw new NotCreatedException("email already taken");
        }
        throw new NotCreatedException("Teacher can not be null");
    }

    public TeacherDTO updateTeacher(Long id, Teacher newUser) {
        if (newUser != null) {
            Teacher user = teacherRepository.findById(id).orElseThrow(
                    () -> new NotFoundException("Teacher: " + id + " was not found")
            );
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
        throw new NotUpdatedException("Teacher: " + id + " is null and didn't update");
    }

    public String deleteTeacher(Long id) {
        if (teacherRepository.existsById(id)) {
            teacherRepository.deleteById(id);
            return "User deleted";
        }
        throw new NotDeletedException("Teacher: " + id + " not found to be deleted");
    }
}