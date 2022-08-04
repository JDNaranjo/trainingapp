package com.jdnt.perficient.training.controller;

import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getUsers(){
        return studentService.getStudents();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id){
        return new ResponseEntity<Student>(studentService.getStudentById(id), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<Student> createStudent(@RequestBody Student newStudent){
        return new ResponseEntity<Student>(studentService.createStudent(newStudent), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student newStudent){
        return new ResponseEntity<Student>(studentService.updateStudent(id, newStudent), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        return new ResponseEntity<String>(studentService.deleteStudent(id), HttpStatus.OK);
    }

}
