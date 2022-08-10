package com.jdnt.perficient.training.controller;

import com.jdnt.perficient.training.DTO.CourseDTO;
import com.jdnt.perficient.training.DTO.SubjectDTO;
import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.service.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return new ResponseEntity<>(courseService.getCourseById(id), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody Course newCourse){
        return new ResponseEntity<>(courseService.createCourse(newCourse), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @Valid @RequestBody Course newCourse){
        return new ResponseEntity<>(courseService.updateCourse(id, newCourse), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id){
        return new ResponseEntity<>(courseService.deleteCourse(id), HttpStatus.OK);
    }

    @PutMapping("/{userId}/{courseId}")
    public ResponseEntity<CourseDTO> enrollUser(@PathVariable Long userId, @PathVariable Long courseId){
        return new ResponseEntity<>(courseService.enrollUser(userId, courseId), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{courseId}/{subjectId}")
    public ResponseEntity<SubjectDTO> assignSubject(@PathVariable Long courseId, @PathVariable Long subjectId){
        return new ResponseEntity<>(courseService.assignSubject(courseId, subjectId), HttpStatus.ACCEPTED);
    }

}
