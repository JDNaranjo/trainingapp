package com.jdnt.perficient.training.service;

import com.jdnt.perficient.training.exception.UserNotCreatedException;
import com.jdnt.perficient.training.exception.UserNotDeletedException;
import com.jdnt.perficient.training.exception.UserNotFoundException;
import com.jdnt.perficient.training.exception.UserNotUpdatedException;
import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.User;
import com.jdnt.perficient.training.repository.CourseRepository;
import com.jdnt.perficient.training.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService{

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    public List<Course> getCourses(){
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id){
        return courseRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    public Course createCourse(Course newCourse){
        if (newCourse != null){
            return courseRepository.save(newCourse);
        }else {
            throw new UserNotCreatedException();
        }
    }

    public Course updateCourse(Long id, Course newCourse) {
        if (courseRepository.existsById(id) && newCourse != null){
            Course course = courseRepository.findById(id).orElseThrow(() -> new UserNotUpdatedException(id));

            course.setName(newCourse.getName());

            return courseRepository.save(course);
        }else{
            throw new UserNotUpdatedException(id);
        }
    }

    public String deleteCourse(Long id) {
        if(courseRepository.existsById(id)){
            courseRepository.deleteById(id);
            return "Course deleted";
        }
        throw new UserNotDeletedException(id);
    }

    public Course enrollUser(Long userId, Long courseId){
        if(courseRepository.existsById(courseId) && userRepository.existsById(userId)){
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new UserNotFoundException(courseId));

            course.getUsersEnrolled().add(user);
            user.setCourse(course);

            userRepository.save(user);
            courseRepository.save(course);

            return courseRepository.findById(courseId).orElseThrow(() -> new UserNotFoundException(courseId));
        }
        throw new UserNotFoundException(userId);
    }

}