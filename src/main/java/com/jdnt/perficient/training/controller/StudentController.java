package com.jdnt.perficient.training.controller;

import com.jdnt.perficient.training.dto.CourseDTO;
import com.jdnt.perficient.training.dto.StudentDTO;
import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path ="api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<StudentDTO> getUsers(){
        return studentService.getStudents();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id){
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{studentId}/{courseId}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long studentId, @PathVariable Long courseId){
        return new ResponseEntity<>(studentService.updateCourse(studentId, courseId), HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/")
    public ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody Student newStudent){
        return new ResponseEntity<>(studentService.createStudent(newStudent), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @Valid @RequestBody Student newStudent){
        return new ResponseEntity<>(studentService.updateStudent(id, newStudent), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        return new ResponseEntity<>(studentService.deleteStudent(id), HttpStatus.OK);
    }

}
