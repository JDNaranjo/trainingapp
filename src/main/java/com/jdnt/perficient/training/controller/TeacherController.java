package com.jdnt.perficient.training.controller;

import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.entity.Teacher;
import com.jdnt.perficient.training.service.StudentService;
import com.jdnt.perficient.training.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping
    public List<Teacher> getTeachers(){
        return teacherService.getTeachers();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id){
        return new ResponseEntity<Teacher>(teacherService.getTeacherById(id), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher newTeacher){
        return new ResponseEntity<Teacher>(teacherService.createTeacher(newTeacher), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher newTeacher){
        return new ResponseEntity<Teacher>(teacherService.updateTeacher(id, newTeacher), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id){
        return new ResponseEntity<String>(teacherService.deleteTeacher(id), HttpStatus.OK);
    }

}
