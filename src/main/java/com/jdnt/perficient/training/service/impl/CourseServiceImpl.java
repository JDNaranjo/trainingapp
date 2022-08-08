package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.DTO.CourseDTO;
import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.entity.Subject;
import com.jdnt.perficient.training.exception.UserNotCreatedException;
import com.jdnt.perficient.training.exception.UserNotDeletedException;
import com.jdnt.perficient.training.exception.UserNotFoundException;
import com.jdnt.perficient.training.exception.UserNotUpdatedException;
import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.User;
import com.jdnt.perficient.training.repository.CourseRepository;
import com.jdnt.perficient.training.repository.StudentRepository;
import com.jdnt.perficient.training.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    public CourseDTO convertCourseToDTO(Course course) {

        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setName(course.getName());

        courseDTO.setStudentsNames(course.getStudentsEnrolled()
                .stream().map(
                        User::getName
                ).collect(Collectors.toSet()));

        courseDTO.setSubjectsNames(course.getSubjects()
                .stream().map(
                        Subject::getName
                ).collect(Collectors.toSet()));

        return courseDTO;
    }

    public List<CourseDTO> getCourses() {
        return courseRepository.findAll().stream()
                .map(this::convertCourseToDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        return convertCourseToDTO(course);
    }

    public CourseDTO createCourse(Course newCourse) {
        if (newCourse != null) {
            courseRepository.save(newCourse);
            return convertCourseToDTO(newCourse);
        }
        throw new UserNotCreatedException();
    }

    public CourseDTO updateCourse(Long id, Course newCourse) {
        if (courseRepository.existsById(id) && newCourse != null) {
            Course course = courseRepository.findById(id)
                    .orElseThrow(() -> new UserNotUpdatedException(id));

            course.setName(newCourse.getName());
            courseRepository.save(course);

            return convertCourseToDTO(course);
        } else {
            throw new UserNotUpdatedException(id);
        }
    }

    public String deleteCourse(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return "Course deleted";
        }
        throw new UserNotDeletedException(id);
    }

    public CourseDTO enrollUser(Long userId, Long courseId) {
        if (courseRepository.existsById(courseId) && studentRepository.existsById(userId)) {
            Student student = studentRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new UserNotFoundException(courseId));

            course.getStudentsEnrolled().add(student);
            student.setCourse(course);

            studentRepository.save(student);
            courseRepository.save(course);

            return convertCourseToDTO(
                    courseRepository.findById(courseId)
                            .orElseThrow(() -> new UserNotFoundException(courseId))
            );
        }
        throw new UserNotFoundException(userId);
    }

}