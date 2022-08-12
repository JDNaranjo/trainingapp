package com.jdnt.perficient.training.controller;

import com.jdnt.perficient.training.dto.SubjectDTO;
import com.jdnt.perficient.training.dto.TeacherDTO;
import com.jdnt.perficient.training.entity.Teacher;
import com.jdnt.perficient.training.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @PutMapping(value = "/{teacherId}/{subjectId}")
    public ResponseEntity<SubjectDTO> updateCourse(@PathVariable Long teacherId, @PathVariable Long subjectId){
        return new ResponseEntity<>(teacherService.updateSubject(teacherId, subjectId), HttpStatus.OK);
    }

    @GetMapping
    public List<TeacherDTO> getTeachers(){
        return teacherService.getTeachers();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable Long id){
        return new ResponseEntity<>(teacherService.getTeacherById(id), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    public ResponseEntity<TeacherDTO> createTeacher(@Valid @RequestBody Teacher newTeacher){
        return new ResponseEntity<>(teacherService.createTeacher(newTeacher), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTO> updateTeacher(@PathVariable Long id, @Valid @RequestBody Teacher newTeacher){
        return new ResponseEntity<>(teacherService.updateTeacher(id, newTeacher), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeacher(@PathVariable Long id){
        return new ResponseEntity<>(teacherService.deleteTeacher(id), HttpStatus.OK);
    }

}
