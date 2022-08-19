package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.dto.SubjectDTO;
import com.jdnt.perficient.training.dto.TeacherDTO;
import com.jdnt.perficient.training.entity.Subject;
import com.jdnt.perficient.training.entity.Teacher;
import com.jdnt.perficient.training.entity.User;
import com.jdnt.perficient.training.exception.NotCreatedException;
import com.jdnt.perficient.training.exception.NotDeletedException;
import com.jdnt.perficient.training.exception.NotFoundException;
import com.jdnt.perficient.training.exception.NotUpdatedException;
import com.jdnt.perficient.training.repository.SubjectRepository;
import com.jdnt.perficient.training.repository.TeacherRepository;
import com.jdnt.perficient.training.repository.UserRepository;
import com.jdnt.perficient.training.service.TeacherService;
import com.jdnt.perficient.training.utility.MapperToDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeacherServiceImplTest {

    @Mock
    TeacherRepository teacherRepository;

    @Mock
    SubjectRepository subjectRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    TeacherService teacherService = new TeacherServiceImpl();

    Teacher teacher;
    Teacher teacher2;
    Subject subject;
    Subject subject2;

    @BeforeEach
    void setUp(){
        teacher = new Teacher();
        teacher.setName("Julio");
        teacher.setLastName("Mara villa");
        teacher.setUsername("jmv");
        teacher.setEmail("jmv@hotmail.com");
        teacher.setPassword("12345");

        teacher2 = new Teacher();
        teacher2.setName("Mike");
        teacher2.setLastName("Woods");
        teacher2.setUsername("mw");
        teacher2.setEmail("mw@gmail.com");
        teacher2.setPassword("54321");

        subject = new Subject();
        subject.setName("UX");
        subject.setDescription("UX Desc");

        subject2 = new Subject();
        subject2.setName("Ing Soft");
        subject2.setDescription("Ing Soft Desc");
    }

    @Test
    void getTeachersTest() {
        List<Teacher> teacherList = new ArrayList<>();
        teacherList.add(teacher);
        teacherList.add(teacher2);

        when(teacherRepository.findAll()).thenReturn(teacherList);

        List<TeacherDTO> convertedList = teacherService.getTeachers();

        List<TeacherDTO> expectedList = new ArrayList<>();
        expectedList.add(MapperToDto.convertTeacherToDTO(teacher));
        expectedList.add(MapperToDto.convertTeacherToDTO(teacher2));

        assertEquals(convertedList, expectedList);
    }

    @Test
    void getTeachersEmptyTest() {
        List<Teacher> teacherList = new ArrayList<>();

        when(teacherRepository.findAll()).thenReturn(teacherList);

        List<TeacherDTO> convertedList = teacherService.getTeachers();

        List<TeacherDTO> expectedList = new ArrayList<>();

        assertEquals(convertedList, expectedList);
    }

    @Test
    void getTeacherByIdTest() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        TeacherDTO convertedTeacher = teacherService.getTeacherById(123L);

        TeacherDTO expectedTeacher = MapperToDto.convertTeacherToDTO(teacher);

        assertEquals(convertedTeacher, expectedTeacher);
    }

    @Test
    void getTeacherByIdNotFoundTest() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> teacherService.getTeacherById(123L)
        );
    }

    @Test
    void createTeacherTest() {
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        TeacherDTO convertTeacher = teacherService.createTeacher(teacher);

        TeacherDTO expectedTeacher = new TeacherDTO();
        expectedTeacher.setName("Julio");
        expectedTeacher.setLastName("Mara villa");
        expectedTeacher.setUsername("jmv");
        expectedTeacher.setEmail("jmv@hotmail.com");

        assertEquals(convertTeacher, expectedTeacher);
    }

    @Test
    void createTeacherRepeatedEmailTest() {
        List<User> userList = new ArrayList<>();
        userList.add(teacher);

        when(userRepository.findAll()).thenReturn(userList);

        assertThrows(NotCreatedException.class,
                () -> teacherService.createTeacher(teacher)
        );
    }

    @Test
    void createTeacherNullTest() {
        assertThrows(NotCreatedException.class,
                () -> teacherService.createTeacher(null)
        );
    }

    @Test
    void createTeacherBlankTest() {
        Teacher teacherBlank = new Teacher();

        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacherBlank);

        TeacherDTO convertTeacher = teacherService.createTeacher(teacherBlank);

        TeacherDTO expectedTeacher = new TeacherDTO();

        assertEquals(convertTeacher, expectedTeacher);
    }

    @Test
    void updateTeacherTest() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher2);

        TeacherDTO convertedTeacher = teacherService.updateTeacher(123L, teacher2);

        TeacherDTO expectedTeacher = MapperToDto.convertTeacherToDTO(teacher2);

        assertEquals(convertedTeacher, expectedTeacher);
    }

    @Test
    void updateTeacherNotFoundTest() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> teacherService.updateTeacher(123L, teacher2)
        );
    }

    @Test
    void updateTeacherNullTest() {
        assertThrows(NotUpdatedException.class,
                () -> teacherService.updateTeacher(123L, null)
        );
    }

    @Test
    void deleteTeacherTest() {
        when(teacherRepository.existsById(anyLong())).thenReturn(true);

        assertEquals("User deleted", teacherService.deleteTeacher(123L));
    }

    @Test
    void deleteTeacherNotExistTest() {
        when(teacherRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NotDeletedException.class,
                () -> teacherService.deleteTeacher(123L)
        );
    }

    @Test
    void updateSubjectTest() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));

        SubjectDTO convertedSubject = teacherService.updateSubject(123L, 321L);

        SubjectDTO expectedSubject = MapperToDto.convertSubjectToDTO(subject);
        expectedSubject.setTeacherName(teacher.getName());

        assertEquals(convertedSubject, expectedSubject);
    }

    @Test
    void updateSubjectNotFoundTeacherTest() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> teacherService.updateSubject(123L, 321L)
        );
    }

    @Test
    void updateSubjectNotFoundSubjectTest() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> teacherService.updateSubject(123L, 321L)
        );
    }

    @Test
    void updateSubjectWithMoreSubjectsTest() {
        teacher.setSubjects(new ArrayList<>());
        teacher.getSubjects().add(subject);

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject2));

        SubjectDTO convertedSubject = teacherService.updateSubject(123L, 321L);

        SubjectDTO expectedSubject = MapperToDto.convertSubjectToDTO(subject2);
        expectedSubject.setTeacherName(teacher.getName());

        assertEquals(convertedSubject, expectedSubject);
    }

    @Test
    void getSubjectsTest() {
        teacher.setSubjects(new ArrayList<>());
        teacher.getSubjects().add(subject);

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));

        List<SubjectDTO> convertedSubjects = teacherService.getSubjects(123L);

        List<SubjectDTO> expectedSubjects = new ArrayList<>();
        expectedSubjects.add(MapperToDto.convertSubjectToDTO(subject));

        assertEquals(convertedSubjects, expectedSubjects);
    }

    @Test
    void getSubjectsNotFoundTest() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> teacherService.getSubjects(123L)
        );
    }

    @Test
    void deleteSubjectTest() {
        teacher.setSubjects(new ArrayList<>());
        teacher.getSubjects().add(subject);

        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));

        assertEquals("Subject deleted", teacherService.deleteSubject(123L, 321L));
    }

    @Test
    void deleteSubjectNotFoundTeacherTest() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> teacherService.deleteSubject(123L, 321L));
    }

    @Test
    void deleteSubjectNotFoundSubjectTest() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> teacherService.deleteSubject(123L, 321L));
    }

    @Test
    void deleteSubjectNotInTeacherTest() {
        when(teacherRepository.findById(anyLong())).thenReturn(Optional.of(teacher));
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));

        assertThrows(NotDeletedException.class,
                () -> teacherService.deleteSubject(123L, 321L)
        );
    }
}