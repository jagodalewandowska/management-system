package com.project.controller;

import com.project.model.FileInfo;
import com.project.model.Projekt;
import com.project.repository.FileRepository;
import com.project.repository.ProjektRepository;
import com.project.service.FileService;
import com.project.service.ProjektService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@RequestMapping("/api")
@Slf4j
public class FileRestController {

  private FileService fileService;
  private FileRepository fileRepository;
  private ProjektService projektService;

  public FileRestController(FileService fileService, FileRepository fileRepository, ProjektService projektService) {
    this.fileService = fileService;
    this.fileRepository = fileRepository;
    this.projektService = projektService;
  }

  @GetMapping("/files")
  Page<FileInfo> getFiles(Pageable pageable) {
    return fileService.getFiles(pageable);
  }

  @GetMapping("/files/{fileId}")
  ResponseEntity<FileInfo> getFileById(@PathVariable Integer fileId) {
    System.out.println("Get files!");
    return ResponseEntity.of(fileService.getFile(fileId));
  }

  @GetMapping("/files/projektId={projektId}")
  public Page<FileInfo> getFilesByProjektId(@PathVariable Integer projektId, Pageable pageable) {
    return fileRepository.findByProjektProjektId(projektId, pageable);
  }

  @PostMapping("/files")
  public ResponseEntity<Void> createFile(@Valid @RequestBody FileInfo fileInfo) {
    FileInfo addedFile = fileService.setFile(fileInfo);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{fileId}")
            .buildAndExpand(addedFile.getFileId()).toUri();
    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("/files/{id}")
  public ResponseEntity<Void> deleteFile(@PathVariable Integer fileId) {
    return fileService.getFile(fileId).map(p -> {
      fileService.deleteFile(fileId);
      return new ResponseEntity<Void> (HttpStatus.OK);
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
