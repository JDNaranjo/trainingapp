package com.jdnt.perficient.training.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Subject {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private String name;
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
