package com.jdnt.perficient.training.service.impl;

import com.jdnt.perficient.training.dto.SubjectDTO;
import com.jdnt.perficient.training.entity.Subject;
import com.jdnt.perficient.training.exception.NotCreatedException;
import com.jdnt.perficient.training.exception.NotDeletedException;
import com.jdnt.perficient.training.exception.NotFoundException;
import com.jdnt.perficient.training.exception.NotUpdatedException;
import com.jdnt.perficient.training.repository.SubjectRepository;
import com.jdnt.perficient.training.service.SubjectService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {

    @Mock
    SubjectRepository subjectRepository;

    @InjectMocks
    SubjectService subjectService = new SubjectServiceImpl();

    Subject subject;
    Subject subject2;

    @BeforeEach
    void setUp(){
        subject = new Subject();
        subject.setName("UX");
        subject.setDescription("UX Desc");

        subject2 = new Subject();
        subject2.setName("Ing Soft");
        subject2.setDescription("Ing Soft Desc");
    }

    @Test
    void getSubjectsTest() {
        List<Subject> subjectList = new ArrayList<>();
        subjectList.add(subject);
        subjectList.add(subject2);

        when(subjectRepository.findAll()).thenReturn(subjectList);

        List<SubjectDTO> convertedList = subjectService.getSubjects();

        List<SubjectDTO> expectedList = new ArrayList<>();
        expectedList.add(MapperToDto.convertSubjectToDTO(subject));
        expectedList.add(MapperToDto.convertSubjectToDTO(subject2));

        assertEquals(convertedList, expectedList);
    }

    @Test
    void getSubjectsEmptyTest() {
        List<Subject> subjectList = new ArrayList<>();

        when(subjectRepository.findAll()).thenReturn(subjectList);

        List<SubjectDTO> convertedList = subjectService.getSubjects();

        List<SubjectDTO> expectedList = new ArrayList<>();

        assertEquals(convertedList, expectedList);
    }

    @Test
    void getSubjectByIdTest() {
        when(subjectRepository.findById(anyLong()))
                .thenReturn(Optional.of(subject));

        SubjectDTO convertedSubject = subjectService.getSubjectById(123L);

        SubjectDTO expectedSubject = MapperToDto.convertSubjectToDTO(subject);

        assertEquals(convertedSubject, expectedSubject);
    }

    @Test
    void getSubjectByIdNotFoundTest() {
        when(subjectRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> subjectService.getSubjectById(123L)
        );
    }

    @Test
    void createSubjectTest() {
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject);

        SubjectDTO convertedSubject = subjectService.createSubject(subject);

        SubjectDTO expectedStudent = MapperToDto.convertSubjectToDTO(subject);
        expectedStudent.setName("UX");
        expectedStudent.setDescription("UX Desc");

        assertEquals(convertedSubject, expectedStudent);
    }

    @Test
    void createSubjectNullTest() {
        assertThrows(NotCreatedException.class,
                () -> subjectService.createSubject(null)
        );
    }

    @Test
    void updateSubjectTest() {
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.of(subject));
        when(subjectRepository.save(any(Subject.class))).thenReturn(subject2);

        SubjectDTO convertedSubject = subjectService.updateSubject(123L, subject2);

        SubjectDTO expectedStudent = MapperToDto.convertSubjectToDTO(subject2);

        assertEquals(convertedSubject, expectedStudent);
    }

    @Test
    void updateSubjectNotFoundTest() {
        when(subjectRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> subjectService.updateSubject(123L, subject2));
    }

    @Test
    void updateSubjectNullTest() {
        assertThrows(NotUpdatedException.class,
                () -> subjectService.updateSubject(123L, null));
    }

    @Test
    void deleteCourseTest() {
        when(subjectRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(subjectRepository).deleteById(anyLong());

        assertEquals("Subject deleted", subjectService.deleteCourse(123L));
    }

    @Test
    void deleteCourseNotFoundTest() {
        when(subjectRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NotDeletedException.class,
                () -> subjectService.deleteCourse(123L));
    }
}