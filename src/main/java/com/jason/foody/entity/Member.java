package com.jason.foody.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @NotBlank(message = "First name can not be empty.")
    String firstName;

    String lastName;

    @NotBlank(message = "Email id can not be empty.")
    @Email(message = "Please provide a valid email.")
    @Column(unique = true)
    String email;

    LocalDateTime created;
    LocalDateTime modified;


}

