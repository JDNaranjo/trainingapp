package com.jdnt.perficient.training.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeacherDTO {
    private String name;
    private String lastName;
    private String email;
    private String username;
    private List<String> subjectNames;
}
