package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.DTO.CourseDTO;
import com.jdnt.perficient.training.DTO.StudentDTO;
import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.exception.UserNotCreatedException;
import com.jdnt.perficient.training.exception.UserNotDeletedException;
import com.jdnt.perficient.training.exception.UserNotFoundException;
import com.jdnt.perficient.training.exception.UserNotUpdatedException;
import com.jdnt.perficient.training.repository.CourseRepository;
import com.jdnt.perficient.training.repository.StudentRepository;
import com.jdnt.perficient.training.service.CourseService;
import com.jdnt.perficient.training.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseService courseService;

    public StudentDTO convertStudentToDTO(Student student) {

        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName(student.getName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setUsername(student.getUsername());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setCourseName(student.getCourse().getName());

        return studentDTO;
    }

    public CourseDTO getCourse(Long id) {
        if(studentRepository.existsById(id)){
            return courseService.convertCourseToDTO(
                    studentRepository.findById(id).get().getCourse()
            );
        }
        throw new UserNotFoundException(id);
    }

    public CourseDTO updateCourse(Long studentId, Long courseId){
        if(studentRepository.existsById(studentId) && courseRepository.existsById(courseId) ){
            Student student = studentRepository.findById(studentId).get();
            student.setCourse(courseRepository.findById(courseId).get());

            Course course = courseRepository.findById(courseId).get();
            if(course.getStudentsEnrolled()!=null){
                course.getStudentsEnrolled().add(student);
            }else {
                course.setStudentsEnrolled(new HashSet<Student>());
                course.getStudentsEnrolled().add(student);
            }

            studentRepository.save(student);
            courseRepository.save(course);

            return courseService.convertCourseToDTO(
                    student.getCourse()
            );
        }
        throw new UserNotUpdatedException(studentId);
    }

    public String deleteCourse(Long studentId) {
        if(studentRepository.existsById(studentId)){
            studentRepository.findById(studentId).get().setCourse(null);
            return "User deleted";
        }
        throw new UserNotDeletedException(studentId);
    }

    public List<StudentDTO> getStudents() {
        return studentRepository.findAll()
                .stream().map(this::convertStudentToDTO)
                .collect(Collectors.toList());
    }

    public StudentDTO getStudentById(Long id) {
        return convertStudentToDTO(
                studentRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id))
        );
    }

    public StudentDTO createStudent(Student newUser) {
        if(newUser!=null){
            return convertStudentToDTO(
                    studentRepository.save(newUser)
            );
        }
        throw new UserNotCreatedException();
    }

    public StudentDTO updateStudent(Long id, Student newUser) {
        if (studentRepository.existsById(id) && newUser != null){
            Student user = studentRepository.findById(id).get();

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