package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tutor {

    private Integer tutorId;
    private String imie;
    private String nazwisko;
    private String email;

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
