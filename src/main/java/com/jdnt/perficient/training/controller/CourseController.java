package com.jdnt.perficient.training.controller;

import com.jdnt.perficient.training.DTO.CourseDTO;
import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.service.impl.CourseServiceImpl;
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
    public List<CourseDTO> getCourses(){
        return courseService.getCourses();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id){
        return new ResponseEntity<CourseDTO>(courseService.getCourseById(id), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<CourseDTO> createCourse(@RequestBody Course newCourse){
        return new ResponseEntity<CourseDTO>(courseService.createCourse(newCourse), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @RequestBody Course newCourse){
        return new ResponseEntity<CourseDTO>(courseService.updateCourse(id, newCourse), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id){
        return new ResponseEntity<String>(courseService.deleteCourse(id), HttpStatus.OK);
    }

    @PutMapping("/{userId}/{courseId}")
    public ResponseEntity<CourseDTO> enrollUser(@PathVariable Long userId, @PathVariable Long courseId){
        return new ResponseEntity<CourseDTO>(courseService.enrollUser(userId, courseId), HttpStatus.OK);
    }

}
