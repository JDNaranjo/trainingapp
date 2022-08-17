package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.dto.CourseDTO;
import com.jdnt.perficient.training.dto.StudentDTO;
import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.exception.NotCreatedException;
import com.jdnt.perficient.training.exception.NotDeletedException;
import com.jdnt.perficient.training.exception.NotFoundException;
import com.jdnt.perficient.training.exception.NotUpdatedException;
import com.jdnt.perficient.training.repository.CourseRepository;
import com.jdnt.perficient.training.repository.StudentRepository;
import com.jdnt.perficient.training.repository.UserRepository;
import com.jdnt.perficient.training.service.StudentService;
import com.jdnt.perficient.training.utility.MapperToDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import static com.jdnt.perficient.training.utility.MapperToDto.convertStudentToDTO;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserRepository userRepository;

    public CourseDTO getCourse(Long id) {
        return MapperToDto.convertCourseToDTO(
                studentRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Course: "+id+" not found"))
                        .getCourse()
        );
    }

    public CourseDTO updateCourse(Long studentId, Long courseId){
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("Student: "+studentId+" not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Course: "+courseId+" not found"));

        student.setCourse(course);

        if(course.getStudentsEnrolled()!=null){
            course.getStudentsEnrolled().add(student);
        }else {
            course.setStudentsEnrolled(new HashSet<>());
            course.getStudentsEnrolled().add(student);
        }

        studentRepository.save(student);
        courseRepository.save(course);

        return MapperToDto.convertCourseToDTO(
                student.getCourse()
        );
    }

    public String deleteCourse(Long studentId) {
        studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student: "+studentId+" not found"))
                .setCourse(null);
        return "User deleted";
    }

    public List<StudentDTO> getStudents() {
        return studentRepository.findAll()
                .stream().map(MapperToDto::convertStudentToDTO)
                .toList();
    }

    public StudentDTO getStudentById(Long id) {
        return convertStudentToDTO(
                studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Student: "+id+" not found"))
        );
    }

    public StudentDTO createStudent(Student newUser) {
        if(newUser!=null){

            long repeated = userRepository.findAll().stream()
                    .filter(user -> user.getEmail().equals(newUser.getEmail())).count();

            if(repeated==0L){
                return convertStudentToDTO(
                        studentRepository.save(newUser)
                );
            }
            throw new NotCreatedException("email already taken");
        }
        throw new NotCreatedException("Student can not be null");
    }

    public StudentDTO updateStudent(Long id, Student newUser) {
        if (newUser != null){
            Student user = studentRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Student:"+ id +" not found"));

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
        throw new NotUpdatedException("Student: "+id+"is null and didn't update");
    }

    public String deleteStudent(Long id) {
        if(studentRepository.existsById(id)){
            studentRepository.deleteById(id);
            return "User deleted";
        }
        throw new NotDeletedException("Student: "+id+" not found to be deleted");
    }
}