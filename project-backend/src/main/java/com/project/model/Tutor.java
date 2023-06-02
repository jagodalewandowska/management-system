package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;
@Entity
@Table(name = "tutor")

public class Tutor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tutor_id")
    private Integer tutorId;

    @NotBlank(message = "Pole imię nie może być puste!")
    @Size(min = 3, max = 50, message = "Imię musi zawierać od {min} do {max} znaków!")
    @Column(nullable = false, length = 50)
    private String imie;

    @NotBlank(message = "Pole nazwisko nie może być puste!")
    @Size(min = 3, max = 100, message = "Nazwisko musi zawierać od {min} do {max} znaków!")
    @Column(nullable = false, length = 100)
    private String nazwisko;

    @NotBlank(message = "Pole email nie może być puste!")
    @Size(min = 3, max = 50, message = "Email musi zawierać od {min} do {max} znaków!")
    @Column(nullable = false, length = 50, unique = true)
    private String email;

//    @ManyToMany(mappedBy = "tutors")
//    @JsonIgnoreProperties({"projekt"})
//    private Set<Projekt> projekty;
//
//
//    @ManyToMany(mappedBy = "tutors")
//    @JsonIgnoreProperties({"tutors"})
//    private Set<Projekt> projektys;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Tutor() {

    }

    public Integer getTutorId() {
        return tutorId;
    }

    public void setTutorId(Integer tutorId) {
        this.tutorId = tutorId;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public Set<Projekt> getProjekty() {
//        return projekty;
//    }
//
//    public void setProjekty(Set<Projekt> projekty) {
//        this.projekty = projekty;
//    }
//
//    public Set<Projekt> getProjektys() {
//        return projektys;
//    }
//
//    public void setProjektys(Set<Projekt> projektys) {
//        this.projektys = projektys;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Tutor(Integer tutorId, String imie, String nazwisko, String email, String password, Role role) {
        this.tutorId = tutorId;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
