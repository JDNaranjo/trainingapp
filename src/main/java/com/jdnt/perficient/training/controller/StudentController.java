package com.jdnt.perficient.training.controller;

import com.jdnt.perficient.training.DTO.CourseDTO;
import com.jdnt.perficient.training.DTO.StudentDTO;
import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<StudentDTO>(studentService.getStudentById(id), HttpStatus.OK);
    }

    @PutMapping(value = "/{studentId}/{courseId}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long studentId, @PathVariable Long courseId){
        return new ResponseEntity<CourseDTO>(studentService.updateCourse(studentId, courseId), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody Student newStudent){
        return new ResponseEntity<StudentDTO>(studentService.createStudent(newStudent), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody Student newStudent){
        return new ResponseEntity<StudentDTO>(studentService.updateStudent(id, newStudent), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id){
        return new ResponseEntity<String>(studentService.deleteStudent(id), HttpStatus.OK);
    }

}
