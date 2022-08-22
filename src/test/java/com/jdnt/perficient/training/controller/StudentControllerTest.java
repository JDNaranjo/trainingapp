package com.jdnt.perficient.training.controller;

import com.jdnt.perficient.training.dto.CourseDTO;
import com.jdnt.perficient.training.dto.StudentDTO;
import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.service.impl.StudentServiceImpl;
import com.jdnt.perficient.training.utility.MapperToDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static com.jdnt.perficient.training.utility.ParseToJson.asJsonString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = StudentController.class)
class StudentControllerTest {

    @MockBean
    StudentServiceImpl studentService;

    @Autowired
    MockMvc mockMvc;

    Student student;
    Student student2;
    Course course;

    @BeforeEach
    void setUp(){
        student = new Student();
        student.setName("Juan");
        student.setLastName("Naranjo");
        student.setUsername("jdnaranjo");
        student.setEmail("jdnt@gamil.com");
        student.setPassword("12345");

        student2 = new Student();
        student2.setName("Diego");
        student2.setLastName("Tafur");
        student2.setUsername("dt1206");
        student2.setEmail("dt@gamil.com");
        student2.setPassword("54321");

        course = new Course();
        course.setName("Ing Soft");
    }

    @Test
    void getUsers() throws Exception {
        List<StudentDTO> studentDTOList = new ArrayList<>();
        studentDTOList.add(MapperToDto.convertStudentToDTO(student));
        studentDTOList.add(MapperToDto.convertStudentToDTO(student2));

        when(studentService.getStudents()).thenReturn(studentDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/student"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Juan")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("Diego")));
    }

    @Test
    void getStudentById() throws Exception {
        when(studentService.getStudentById(anyLong()))
                .thenReturn(MapperToDto.convertStudentToDTO(student));

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/student/{id}", 123L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Juan")));
    }

    @Test
    void updateCourse() throws Exception {

    }

    @Test
    void createStudent() throws Exception {
        when(studentService.createStudent(any(Student.class)))
                .thenReturn(MapperToDto.convertStudentToDTO(student));

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/student/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(student))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Juan")));
    }

    @Test
    void updateStudent() {
    }

    @Test
    void deleteStudent() {
    }
}