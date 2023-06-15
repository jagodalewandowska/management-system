package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Student {

    private Integer studentId;
   // @NotBlank(message = "Pole imię nie może być puste!")
    private String imie;
    private String nazwisko;
    private String nrIndeksu;
    @Email(message = "Niepoprawny format adresu e-mail.")
    private String email;
    @Value("false")
    private Boolean stacjonarny;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Size(min=8, max=32,
            message = "Hasło musi składać się z przynajmniej 8 znaków i nie przekraczać 32.")
    private String password;

    private Role role;
    private Set<Projekt> projekty;

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


    public void setStacjonarny(boolean stacjonarny) {
        this.stacjonarny = stacjonarny;
    }

    public Boolean getStacjonarny() {
        return stacjonarny;
    }

    public void setStacjonarny(Boolean stacjonarny) {
        this.stacjonarny = stacjonarny;
    }

    public Student() {}
    public Student(Integer studentId, String imie, String nazwisko, String nrIndeksu, String email, Boolean stacjonarny) {
        super();
        this.studentId = studentId;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.nrIndeksu = nrIndeksu;
        this.email = email;
        this.stacjonarny = stacjonarny;
    }

//    public Student(String imie, String nazwisko, String nrIndeksu, String email, Boolean stacjonarny) {
//        this.imie = imie;
//        this.nazwisko = nazwisko;
//        this.nrIndeksu = nrIndeksu;
//        this.email = email;
//        this.stacjonarny = stacjonarny;
//    }

//    public Student(Integer studentId, String imie, String nazwisko, String nrIndeksu, String email,
//                   Boolean stacjonarny, Set<Projekt> projekty) {
//        this.studentId = studentId;
//        this.imie = imie;
//        this.nazwisko = nazwisko;
//        this.nrIndeksu = nrIndeksu;
//        this.email = email;
//        this.stacjonarny = stacjonarny;
//        this.projekty = projekty;
//    }
}
