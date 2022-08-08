package com.jdnt.perficient.training.entity;

import lombok.*;

import javax.persistence.*;
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
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    @ToString.Exclude
    private Set<Student> studentsEnrolled;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    @ToString.Exclude
    private Set<Subject> subjects;

}