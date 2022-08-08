package com.jdnt.perficient.training.DTO;

import lombok.Data;

import java.util.Set;

@Data
public class CourseDTO {

    private String name;
    private Set<String> studentsNames;
    private Set<String> subjectsNames;

}
