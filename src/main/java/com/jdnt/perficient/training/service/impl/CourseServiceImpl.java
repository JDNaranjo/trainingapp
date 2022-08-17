package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.dto.CourseDTO;
import com.jdnt.perficient.training.dto.SubjectDTO;
import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.entity.Subject;
import com.jdnt.perficient.training.exception.NotCreatedException;
import com.jdnt.perficient.training.exception.NotDeletedException;
import com.jdnt.perficient.training.exception.NotFoundException;
import com.jdnt.perficient.training.exception.NotUpdatedException;
import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.repository.CourseRepository;
import com.jdnt.perficient.training.repository.StudentRepository;
import com.jdnt.perficient.training.repository.SubjectRepository;
import com.jdnt.perficient.training.service.CourseService;
import com.jdnt.perficient.training.utility.MapperToDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

import static com.jdnt.perficient.training.utility.MapperToDto.convertCourseToDTO;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    SubjectRepository subjectRepository;

    public List<CourseDTO> getCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream()
                .map(MapperToDto::convertCourseToDTO)
                .toList();
    }

    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Course: "+id+" not found"));

        return convertCourseToDTO(course);
    }

    public CourseDTO createCourse(Course newCourse) {
        if (newCourse != null) {
            courseRepository.save(newCourse);
            return convertCourseToDTO(newCourse);
        }
        throw new NotCreatedException("Course can not be null");
    }

    public CourseDTO updateCourse(Long id, Course newCourse) {
        if (newCourse != null) {
            Course course = courseRepository.findById(id)
                    .orElseThrow(() -> new NotUpdatedException("Course: "+id+"was not found"));

            course.setName(newCourse.getName());
            courseRepository.save(course);

            return convertCourseToDTO(course);
        }
        throw  new NotUpdatedException("Course: "+id+"is null and didn't update");
    }

    public String deleteCourse(Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
            return "Course deleted";
        }
        throw new NotDeletedException("Course: "+id+" not found to be deleted");
    }

    public CourseDTO enrollUser(Long userId, Long courseId) {
        Student student = studentRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Student: "+userId+" not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Course: "+courseId+" not found"));

        if(course.getStudentsEnrolled() != null){
            course.getStudentsEnrolled().add(student);
        }else {
            course.setStudentsEnrolled(new HashSet<>());
            course.getStudentsEnrolled().add(student);
        }

        student.setCourse(course);

        studentRepository.save(student);
        courseRepository.save(course);

        return convertCourseToDTO(
                courseRepository.findById(courseId)
                        .orElseThrow(() -> new NotFoundException("Course: "+courseId+" not found"))
        );
    }

    public SubjectDTO assignSubject(Long courseId, Long subjectId) {
        Course course = courseRepository.findById(courseId).orElseThrow(
                () -> new NotFoundException("Course: "+courseId+" not found"));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new NotFoundException("Subject: "+subjectId+" not found"));

        subject.setCourse(course);

        if (course.getSubjects()!= null) {
            course.getSubjects().add(subject);
        }else {
            course.setSubjects(new HashSet<>());
            course.getSubjects().add(subject);
        }

        courseRepository.save(course);
        subjectRepository.save(subject);

        return MapperToDto.convertSubjectToDTO(subject);

    }
}