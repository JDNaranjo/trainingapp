package com.jdnt.perficient.training.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Teacher extends User{

    @OneToMany
    @JsonIgnore
    @ToString.Exclude
    private  List<Subject> subjects;

    public Teacher(String name, String lastName, @Email @NotBlank String email, String username,
                   @NotBlank String password, List<Subject> subjects) {
        super(name, lastName, email, username, password);
        this.subjects = subjects;
    }
}