package com.jdnt.perficient.training.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Student extends User {

    @ManyToOne
    @JsonIgnore
    private Course course;

    public Student(String name, String lastName, @Email @NotBlank String email, String username,
                   @NotBlank String password) {
        super(name, lastName, email, username, password);
    }

}
