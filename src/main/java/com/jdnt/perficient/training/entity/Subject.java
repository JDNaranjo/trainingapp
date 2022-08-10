package com.jdnt.perficient.training.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Subject {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long id;
    @NotBlank(message = "The name is required")
    @Size(min = 3, max = 100, message = "Name needs to be between 3 and 100 characters")
    private String name;
    @NotBlank(message = "Description is required")
    @Size(min = 5, max = 200, message = "Description needs to be between 5 and 200 characters")
    private String description;

    @ManyToOne
    @JsonIgnore
    private Course course;

    @OneToOne
    @JsonIgnore
    private Teacher teacher;

    public Subject(String name, String description, Course course, Teacher teacher) {
        this.name = name;
        this.description = description;
        this.course = course;
        this.teacher = teacher;
    }
}
