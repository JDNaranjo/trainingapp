package com.jdnt.perficient.training.controller;

import com.jdnt.perficient.training.dto.CourseDTO;
import com.jdnt.perficient.training.entity.Course;
import com.jdnt.perficient.training.entity.Student;
import com.jdnt.perficient.training.entity.Subject;
import com.jdnt.perficient.training.service.impl.CourseServiceImpl;
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

import java.util.*;

import static com.jdnt.perficient.training.utility.ParseToJson.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CourseController.class)
class CourseControllerTest {

    @MockBean
    private CourseServiceImpl courseService;

    @Autowired
    private MockMvc mockMvc;

    Course course;
    Course course2;
    Student student;
    Subject subject;

    @BeforeEach
    void setUp(){
        course = new Course();
        course.setName("Admin");

        course2 = new Course();
        course2.setName("Ing Tel");

        student = new Student();
        student.setName("Juan");
        student.setLastName("Naranjo");
        student.setUsername("jdnaranjo");
        student.setEmail("jdnt@gamil.com");
        student.setPassword("12345");

        subject = new Subject();
        subject.setName("Ing Soft");
        subject.setDescription("Ing Soft Desc");
    }

    @Test
    void getCoursesTest() throws Exception {
        List<CourseDTO> courseDTOList = new ArrayList<>();
        courseDTOList.add(MapperToDto.convertCourseToDTO(course));
        courseDTOList.add(MapperToDto.convertCourseToDTO(course2));

        when(courseService.getCourses()).thenReturn(courseDTOList);

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/course"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Admin")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("Ing Tel")));
    }

    @Test
    void getCourseByIdTest() throws Exception {
        when(courseService.getCourseById(anyLong())).thenReturn(MapperToDto.convertCourseToDTO(course));

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/course/{id}", 123L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Admin")));
    }

    @Test
    void createCourseTest() throws Exception {
        when(courseService.createCourse(any(Course.class))).thenReturn(MapperToDto.convertCourseToDTO(course));

        mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/course/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(course))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Admin")));
    }

    @Test
    void updateCourseTest() throws Exception {
        when(courseService.updateCourse(anyLong(), any(Course.class)))
                .thenReturn(MapperToDto.convertCourseToDTO(course2));

        mockMvc.perform(MockMvcRequestBuilders.put("http://localhost:8080/api/course/{id}"
                    , 123L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(course2))
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Ing Tel")));
    }

    @Test
    void deleteCourseTest() throws Exception {
        Map<String, String> body = new HashMap<>();
        body.put("result", "Course deleted");
        when(courseService.deleteCourse(anyLong()))
                .thenReturn(body);

        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/course/{id}", 19L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result", Matchers.is("Course deleted")));
    }

    @Test
    void enrollUserTest() throws Exception {
        course.setStudentsEnrolled(new HashSet<>());
        course.getStudentsEnrolled().add(student);

        when(courseService.enrollUser(anyLong(), anyLong()))
                .thenReturn(MapperToDto.convertCourseToDTO(course));

        mockMvc.perform(MockMvcRequestBuilders
                .put("http://localhost:8080/api/course//{userId}/{courseId}", 123L, 321L))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.studentsNames.size()", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers
                        .jsonPath("$.studentsNames.[0]", Matchers.is("Juan")));
    }

    @Test
    void assignSubjectTest() throws Exception {
        subject.setCourse(course);
        when(courseService.assignSubject(anyLong(), anyLong()))
                .thenReturn(MapperToDto.convertSubjectToDTO(subject));

        mockMvc.perform(MockMvcRequestBuilders
                .patch("http://localhost:8080/api/course//{courseId}/{subjectId}", 123L, 321L))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.courseName", Matchers.is("Admin")));
    }
}