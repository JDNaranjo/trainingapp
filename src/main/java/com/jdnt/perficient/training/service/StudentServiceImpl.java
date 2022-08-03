package com.jdnt.perficient.training.service;

import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.exception.UserNotDeletedException;
import com.jdnt.perficient.training.exception.UserNotFoundException;
import com.jdnt.perficient.training.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    StudentRepository studentRepository;

    public Course getCourse(Long id) {
        if(studentRepository.existsById(id)){
            return studentRepository.findById(id).get().getCourse();
        }else {
            throw new UserNotFoundException(id);
        }
    }

    public String deleteCourse(Long id) {
        if(studentRepository.existsById(id)){
            studentRepository.deleteById(id);
            return "User deleted";
        }
        throw new UserNotDeletedException(id);
    }

    public List<Student> getStudents() {
        return null;
    }

    public Student getStudentById(Long id) {
        return null;
    }

    public Student createStudent(Student newUser) {
        return null;
    }

    public Student updateStudent(Long id, Student newUser) {
        return null;
    }

    public String deleteStudent(Long id) {
        return null;
    }


}