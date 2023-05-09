package com.project.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
//import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="projekt") //TODO Indeksować kolumny, które są najczęściej wykorzystywane do wyszukiwania projektów
public class Projekt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="projekt_id") //tylko jeżeli nazwa kolumny w bazie danych ma być inna od nazwy zmiennej
    private Integer projektId;

    @NotBlank(message = "Pole nazwa nie może być puste!")
    @Size(min = 3, max = 50, message = "Nazwa musi zawierać od {min} do {max} znaków!")
    @Column(nullable = false, length = 50)
    private String nazwa;

    @Column(nullable = true, length = 1000)
    private String opis;

//    @Column(nullable = false)
//    private Timestamp dataczas_utworzenia;

    @Column(name = "dataOddania")
    private LocalDate dataOddania;

    @CreationTimestamp
    @Column(name = "dataczas_utworzenia", nullable = false, updatable = false)
    private LocalDateTime dataCzasUtworzenia;

    @UpdateTimestamp
    @Column(name = "dataczas_modyfikacji", nullable = false)
    private LocalDateTime dataCzasModyfikacji;

    @OneToMany(mappedBy = "projekt")
    @JsonIgnoreProperties(value = {"projekt"}, allowSetters = true)
    private List<Zadanie> zadania;

    @ManyToMany
    @JoinTable(name = "projekt_student",
            joinColumns = {@JoinColumn(name="projekt_id")},
            inverseJoinColumns = {@JoinColumn(name="student_id")})
    private Set<Student> studenci;

    public Set<Student> getStudenci() {
        return studenci;
    }

    public void setStudenci(Set<Student> studenci) {
        this.studenci = studenci;
    }

    public Integer getProjektId() {
        return projektId;
    }

    public void setProjektId(Integer projektId) {
        this.projektId = projektId;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

//    public Timestamp getDataczas_utworzenia() {
//        return dataczas_utworzenia;
//    }
//
//    public void setDataczas_utworzenia(Timestamp dataczas_utworzenia) {
//        this.dataczas_utworzenia = dataczas_utworzenia;
//    }


    public LocalDate getDataOddania() {
        return dataOddania;
    }

    public void setDataOddania(LocalDate dataOddania) {
        this.dataOddania = dataOddania;
    }

    public LocalDateTime getDataCzasUtworzenia() {
        return dataCzasUtworzenia;
    }

    public void setDataCzasUtworzenia(LocalDateTime dataCzasUtworzenia) {
        this.dataCzasUtworzenia = dataCzasUtworzenia;
    }

    public LocalDateTime getDataCzasModyfikacji() {
        return dataCzasModyfikacji;
    }

    public void setDataCzasModyfikacji(LocalDateTime dataCzasModyfikacji) {
        this.dataCzasModyfikacji = dataCzasModyfikacji;
    }

    public List<Zadanie> getZadania() {
        return zadania;
    }

    public void setZadania(List<Zadanie> zadania) {
        this.zadania = zadania;
    }

    // konstruktory?

    public Projekt() {}

    public Projekt(String nazwa, String opis, LocalDate dataOddania) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.dataOddania = dataOddania;
    }

    public Projekt(Integer projektId, String nazwa, String opis, LocalDateTime dataCzasUtworzenia, LocalDate dataOddania) {
        super();
        this.projektId = projektId;
        this.nazwa = nazwa;
        this.opis = opis;
        this.dataCzasUtworzenia = dataCzasUtworzenia;
        this.dataOddania = dataOddania;
    }
}
