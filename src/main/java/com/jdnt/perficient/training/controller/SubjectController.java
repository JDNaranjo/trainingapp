package com.jdnt.perficient.training.controller;

import com.jdnt.perficient.training.entity.Subject;
import com.jdnt.perficient.training.service.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path ="api/subject")
public class SubjectController {

    @Autowired
    private SubjectServiceImpl subjectService;

    @GetMapping
    public List<Subject> getSubject(){
        return subjectService.getSubjects();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Subject> getCourseById(@PathVariable Long id){
        return new ResponseEntity<Subject>(subjectService.getSubjectById(id), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<Subject> createCourse(@RequestBody Subject newSubject){
        return new ResponseEntity<Subject>(subjectService.createSubject(newSubject), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody Subject newSubject){
        return new ResponseEntity<Subject>(subjectService.updateSubject(id, newSubject), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id){
        return new ResponseEntity<String>(subjectService.deleteCourse(id), HttpStatus.OK);
    }

}
