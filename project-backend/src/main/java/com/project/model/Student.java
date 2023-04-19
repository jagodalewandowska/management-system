package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity //Indeksujemy kolumny, które są najczęściej wykorzystywane do wyszukiwania studentów
@Table(name = "student",
        indexes = { @Index(name = "idx_nazwisko", columnList = "nazwisko", unique = false),
                @Index(name = "idx_nr_indeksu", columnList = "nr_indeksu", unique = true) })

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="student_id")
    private Integer studentId;

    @NotBlank(message = "Pole imię nie może być puste!")
    @Size(min = 3, max = 50, message = "Imię musi zawierać od {min} do {max} znaków!")
    @Column(nullable = false, length = 50)
    private String imie;

    @NotBlank(message = "Pole nazwisko nie może być puste!")
    @Size(min = 3, max = 100, message = "Nazwisko musi zawierać od {min} do {max} znaków!")
    @Column(nullable = false, length = 100)
    private String nazwisko;

    @NotBlank(message = "Pole nr_indeksu nie może być puste!")
    @Column(name="nr_indeksu", nullable = false, length = 20, unique = true)
    private String nrIndeksu;

    @NotBlank(message = "Pole email nie może być puste!")
    @Size(min = 3, max = 50, message = "Email musi zawierać od {min} do {max} znaków!")
    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean stacjonarny;

    @ManyToMany(mappedBy = "studenci")
    @JsonManagedReference
    private Set<Projekt> projekty;

    public Set<Projekt> getProjekty() {
        return projekty;
    }

    public void setProjekty(Set<Projekt> projekty) {
        this.projekty = projekty;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
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

    public String getNrIndeksu() {
        return nrIndeksu;
    }

    public void setNrIndeksu(String nrIndeksu) {
        this.nrIndeksu = nrIndeksu;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStacjonarny() {
        return stacjonarny;
    }

    public void setStacjonarny(boolean stacjonarny) {
        this.stacjonarny = stacjonarny;
    }

    public Student() {}
    public Student(String imie, String nazwisko, String nrIndeksu, Boolean stacjonarny) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nrIndeksu = nrIndeksu;
    }
    public Student(String imie, String nazwisko, String nrIndeksu, String email, Boolean stacjonarny) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nrIndeksu = nrIndeksu;
        this.email = email;
        this.stacjonarny = stacjonarny;
    }
}
