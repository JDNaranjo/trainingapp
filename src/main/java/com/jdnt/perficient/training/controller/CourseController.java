package com.jdnt.perficient.training.controller;

import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.service.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path ="api/course")
public class CourseController {

    @Autowired
    private CourseServiceImpl courseService;

    @GetMapping
    public List<Course> getCourses(){
        return courseService.getCourses();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id){
        return new ResponseEntity<Course>(courseService.getCourseById(id), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<Course> createCourse(@RequestBody Course newCourse){
        return new ResponseEntity<Course>(courseService.createCourse(newCourse), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course newCourse){
        return new ResponseEntity<Course>(courseService.updateCourse(id, newCourse), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id){
        return new ResponseEntity<String>(courseService.deleteCourse(id), HttpStatus.OK);
    }

    @PutMapping("/{userId}/{courseId}")
    public ResponseEntity<Course> enrollUser(@PathVariable Long userId, @PathVariable Long courseId){
        return new ResponseEntity<Course>(courseService.enrollUser(userId, courseId), HttpStatus.OK);
    }

}
