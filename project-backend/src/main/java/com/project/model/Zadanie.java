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
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.bytecode.internal.bytebuddy.BytecodeProviderImpl;

import java.time.LocalDateTime;

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
    private Integer kolejnosc;

    @Column(nullable = true, length = 1000)
    private String opis;

//    @NotBlank(message = "Pole dataczas_dodania nie może być puste!")
    @CreationTimestamp
    @Column(name = "dataczas_dodania", nullable = false, updatable = false)
    private LocalDateTime dataczas_dodania;

    @ManyToOne
    @JsonIgnoreProperties({"projekt"})
    @JoinColumn(name = "projekt_id")
    private Projekt projekt;



    public Zadanie() {

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

    public Integer getKolejnosc() {
        return kolejnosc;
    }

    public void setKolejnosc(Integer kolejnosc) {
        this.kolejnosc = kolejnosc;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public LocalDateTime getDataczas_dodania() {
        return dataczas_dodania;
    }

    public void setDataczas_dodania(LocalDateTime dataczas_dodania) {
        this.dataczas_dodania = dataczas_dodania;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        System.out.println(projekt.getProjektId());
        this.projekt = projekt;
    }

    public Zadanie(String nazwa, String opis, Integer kolejnosc) {
        this.nazwa = nazwa;
        this.kolejnosc = kolejnosc;
        this.opis = opis;
    }

    public Zadanie(Integer zadanieId, String nazwa, String opis, Integer kolejnosc, LocalDateTime dataczas_dodania){
        super();
        this.zadanieId = zadanieId;
        this.nazwa = nazwa;
        this.opis = opis;
        this.kolejnosc = kolejnosc;
        this.dataczas_dodania = dataczas_dodania;
    }
}