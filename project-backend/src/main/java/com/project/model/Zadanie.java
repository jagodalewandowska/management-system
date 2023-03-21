package com.project.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;

@Entity
@Table(name="zadanie")
public class Zadanie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="zadanie_id")
    private Integer zadanieId;

    @NotBlank(message = "Pole nazwa nie może być puste!")
    @Size(min = 3, max = 50, message = "Nazwa musi zawierać od {min} do {max} znaków!")
    @Column(nullable = false, length = 50)
    private String nazwa;

    @Column(nullable = true)
    private int kolejnosc;

    @Column(nullable = true, length = 1000)
    private String opis;

    @NotBlank(message = "Pole dataczas_dodania nie może być puste!")
    @Column(nullable = false)
    private Timestamp dataczas_dodania;

    @ManyToOne
    @JsonIgnoreProperties({"projekt"})
    @JoinColumn(name = "projekt_id")
    private Projekt projekt;

    public Zadanie(String nazwa, String opis, int kolejnosc) {
    }

    public Integer getZadanieId() {
        return zadanieId;
    }

    public void setZadanieId(Integer zadanieId) {
        this.zadanieId = zadanieId;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getKolejnosc() {
        return kolejnosc;
    }

    public void setKolejnosc(int kolejnosc) {
        this.kolejnosc = kolejnosc;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Timestamp getDataczas_dodania() {
        return dataczas_dodania;
    }

    public void setDataczas_dodania(Timestamp dataczas_dodania) {
        this.dataczas_dodania = dataczas_dodania;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }

    public Zadanie() {}

    public Zadanie(String nazwa, int kolejnosc, String opis) {
        this.nazwa = nazwa;
        this.kolejnosc = kolejnosc;
        this.opis = opis;
    }
}