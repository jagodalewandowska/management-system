package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Entity
@Table(name = "files")
public class FileInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="file_id")
    private Integer fileId;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @ManyToOne
    @JsonIgnoreProperties({"projekt"})
    @JoinColumn(name = "projekt_id")
    private Projekt projekt;

    public FileInfo() {

    }

    public FileInfo(Integer fileId, String name, String url, Projekt projekt) {
        super();
        this.fileId = fileId;
        this.name = name;
        this.url = url;
        this.projekt = projekt;
    }

    public FileInfo(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public FileInfo(String name, String url, Projekt projekt) {
        this.name = name;
        this.url = url;
        this.projekt = projekt;
    }

    public Projekt getProjekt() {
        return projekt;
    }

    public void setProjekt(Projekt projekt) {
        this.projekt = projekt;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public Integer getId() {
        return fileId;
    }

    public void setId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

