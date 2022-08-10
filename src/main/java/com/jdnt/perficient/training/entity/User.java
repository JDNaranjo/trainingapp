package com.jdnt.perficient.training.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
@Setter
@Getter
@ToString
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name needs to be between 3 and 100 characters")
    private String name;

    @Size(min = 3, max = 100, message = "Last name needs to be between 3 and 100 characters")
    private String lastName;
    @Email(message = "Email is invalid")
    @NotBlank(message = "Email is required")
    private String email;
    @Size(min = 5, max = 20, message = "Last name needs to be between 5 and 20 characters")
    @NotBlank(message = "Username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;

    public User() {
    }

    public User(String name, String lastName, String email, String username, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
