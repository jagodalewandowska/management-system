package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Zadanie {
    private Integer zadanieId;

    @NotBlank(message = "Pole nazwa nie może być puste!")
    @Size(min = 3, max = 50, message = "Nazwa musi zawierać od {min} do {max} znaków!")
    private String nazwa;

    private int kolejnosc;

    private String opis;

    @NotBlank(message = "Pole dataczas_dodania nie może być puste!")
    private Timestamp dataczas_dodania;

    @JsonIgnoreProperties({"projekt"})
    private Projekt projekt;

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