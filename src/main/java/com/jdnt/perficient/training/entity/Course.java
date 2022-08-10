package com.jdnt.perficient.training.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Course {

    @Id
    @GeneratedValue(strategy =  GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name needs to be between 3 and 100 characters")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    @ToString.Exclude
    private Set<Student> studentsEnrolled;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    @ToString.Exclude
    private Set<Subject> subjects;

}