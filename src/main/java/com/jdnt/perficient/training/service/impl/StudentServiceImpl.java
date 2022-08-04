package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.exception.UserNotCreatedException;
import com.jdnt.perficient.training.exception.UserNotDeletedException;
import com.jdnt.perficient.training.exception.UserNotFoundException;
import com.jdnt.perficient.training.exception.UserNotUpdatedException;
import com.jdnt.perficient.training.repository.StudentRepository;
import com.jdnt.perficient.training.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    public Course getCourse(Long id) {
        if(studentRepository.existsById(id)){
            return studentRepository.findById(id).get().getCourse();
        }else {
            throw new UserNotFoundException(id);
        }
    }

    public String deleteCourse(Long studentId) {
        if(studentRepository.existsById(studentId)){
            studentRepository.findById(studentId).get().setCourse(null);
            return "User deleted";
        }
        throw new UserNotDeletedException(studentId);
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public Student createStudent(Student newUser) {
        if(newUser!=null && !studentRepository.existsById(newUser.getId())){
            return studentRepository.save(newUser);
        }
        throw new UserNotCreatedException();
    }

    public Student updateStudent(Long id, Student newUser) {
        if (studentRepository.existsById(id) && newUser != null){
            Student user = studentRepository.findById(id).get();

            user.setEmail(newUser.getEmail());
            user.setLastName(newUser.getLastName());
            user.setName(newUser.getName());
            user.setPassword(newUser.getPassword());
            user.setUsername(newUser.getUsername());
            user.setCourse(newUser.getCourse());

            return studentRepository.save(user);
        }
        throw new UserNotUpdatedException(id);
    }

    public String deleteStudent(Long id) {
        if(studentRepository.existsById(id)){
            studentRepository.deleteById(id);
            return "User deleted";
        }
        throw new UserNotDeletedException(id);
    }
}