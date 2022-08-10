package com.jdnt.perficient.training.controller;

import com.jdnt.perficient.training.DTO.SubjectDTO;
import com.jdnt.perficient.training.entity.Subject;
import com.jdnt.perficient.training.service.impl.SubjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path ="api/subject")
public class SubjectController {

    @Autowired
    private SubjectServiceImpl subjectService;

    @GetMapping
    public List<SubjectDTO> getSubject(){
        return subjectService.getSubjects();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SubjectDTO> getCourseById(@PathVariable Long id){
        return new ResponseEntity<>(subjectService.getSubjectById(id), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<SubjectDTO> createCourse(@Valid @RequestBody Subject newSubject){
        return new ResponseEntity<>(subjectService.createSubject(newSubject), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDTO> updateSubject(@PathVariable Long id, @Valid @RequestBody Subject newSubject){
        return new ResponseEntity<>(subjectService.updateSubject(id, newSubject), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long id){
        return new ResponseEntity<>(subjectService.deleteCourse(id), HttpStatus.OK);
    }

}
