package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Zadanie {
    private Integer zadanieId;
    private String nazwa;
    private Integer kolejnosc;
    private String opis;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp dataczas_dodania;

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

    public Zadanie(Integer zadanieId, String nazwa, Integer kolejnosc, String opis, Timestamp dataczas_dodania,
                   Projekt projekt) {
        super();
        this.zadanieId = zadanieId;
        this.nazwa = nazwa;
        this.kolejnosc = kolejnosc;
        this.opis = opis;
        this.dataczas_dodania = dataczas_dodania;
        this.projekt = projekt;
    }

    public Zadanie(Projekt projekt, String nazwa, Integer kolejnosc, String opis) {
        this.projekt = projekt;
        this.nazwa = nazwa;
        this.kolejnosc = kolejnosc;
        this.opis = opis;
    }

}