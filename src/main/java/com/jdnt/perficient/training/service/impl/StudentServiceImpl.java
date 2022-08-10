package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.DTO.CourseDTO;
import com.jdnt.perficient.training.DTO.StudentDTO;
import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.entity.User;
import com.jdnt.perficient.training.exception.UserNotCreatedException;
import com.jdnt.perficient.training.exception.UserNotDeletedException;
import com.jdnt.perficient.training.exception.UserNotFoundException;
import com.jdnt.perficient.training.exception.UserNotUpdatedException;
import com.jdnt.perficient.training.repository.CourseRepository;
import com.jdnt.perficient.training.repository.StudentRepository;
import com.jdnt.perficient.training.repository.UserRepository;
import com.jdnt.perficient.training.service.CourseService;
import com.jdnt.perficient.training.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseService courseService;
    @Autowired
    UserRepository userRepository;

    public StudentDTO convertStudentToDTO(Student student) {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(student.getName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setUsername(student.getUsername());
        studentDTO.setEmail(student.getEmail());

        if (student.getCourse()!=null)
            studentDTO.setCourseName(student.getCourse().getName());

        return studentDTO;
    }

    public CourseDTO getCourse(Long id) {
        return courseService.convertCourseToDTO(
                studentRepository.findById(id)
                        .orElseThrow(() -> new UserNotFoundException(id))
                        .getCourse()
        );
    }

    public CourseDTO updateCourse(Long studentId, Long courseId){
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new UserNotFoundException(studentId));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new UserNotFoundException(courseId));

        student.setCourse(course);

        if(course.getStudentsEnrolled()!=null){
            course.getStudentsEnrolled().add(student);
        }else {
            course.setStudentsEnrolled(new HashSet<>());
            course.getStudentsEnrolled().add(student);
        }

        studentRepository.save(student);
        courseRepository.save(course);

        return courseService.convertCourseToDTO(
                student.getCourse()
        );
    }

    public String deleteCourse(Long studentId) {
        studentRepository.findById(studentId)
                .orElseThrow(() -> new UserNotFoundException(studentId))
                .setCourse(null);
        return "User deleted";
    }

    public List<StudentDTO> getStudents() {
        return studentRepository.findAll()
                .stream().map(this::convertStudentToDTO)
                .toList();
    }

    public StudentDTO getStudentById(Long id) {
        return convertStudentToDTO(
                studentRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id))
        );
    }

    public StudentDTO createStudent(Student newUser) {
        if(newUser!=null){

            long repited = userRepository.findAll().stream()
                    .filter(user -> user.getEmail().equals(newUser.getEmail())).count();

            if(repited==0){
                return convertStudentToDTO(
                        studentRepository.save(newUser)
                );
            }
            throw new UserNotCreatedException("email already taken");
        }
        throw new UserNotCreatedException("Student can not be null");
    }

    public StudentDTO updateStudent(Long id, Student newUser) {
        if (newUser != null){
            Student user = studentRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException(id));

            user.setEmail(newUser.getEmail());
            user.setLastName(newUser.getLastName());
            user.setName(newUser.getName());
            user.setPassword(newUser.getPassword());
            user.setUsername(newUser.getUsername());
            user.setCourse(newUser.getCourse());

            return convertStudentToDTO(
                    studentRepository.save(user)
            );
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