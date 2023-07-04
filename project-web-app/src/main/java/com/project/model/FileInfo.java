package com.project.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FileInfo {

  private Integer fileId;
  private String name;
  private String url;
  private Projekt projekt;

  public FileInfo(String name, String url, Projekt projekt) {
    this.name = name;
    this.url = url;
    this.projekt = projekt;
  }

    public FileInfo() {

    }

  public FileInfo(Integer fileId, String name, String url, Projekt projekt) {
    this.fileId = fileId;
    this.name = name;
    this.url = url;
    this.projekt = projekt;
  }

  public FileInfo(Integer fileId, String name, String url) {
    this.fileId = fileId;
    this.name = name;
    this.url = url;
  }



  public Integer getFileId() {
    return fileId;
  }

  public void setFileId(Integer fileId) {
    this.fileId = fileId;
  }

  public Projekt getProjekt() {
    return projekt;
  }

  public void setProjekt(Projekt projekt) {
    this.projekt = projekt;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return this.url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
