package com.project.controller;

import com.project.model.FileInfo;
import com.project.service.FileService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api")
@Slf4j
public class FileRestController {

  private FileService fileService;

  public FileRestController(FileService fileService) {
    this.fileService = fileService;
  }

  @GetMapping("/files")
  ResponseEntity<List<FileInfo>> getFiles() {
    log.info("Files accessed!");
    List<FileInfo> files = fileService.getFiles();
    return ResponseEntity.of(Optional.ofNullable(fileService.getFiles()));
  }

  @GetMapping("/files/{fileId}")
  ResponseEntity<FileInfo> getFileById(@PathVariable Integer fileId) {
    log.info("Files accessed!");
    return ResponseEntity.of(fileService.getFile(fileId));
  }

  @PostMapping("/files")
  public ResponseEntity<Void> createFile(@Valid @RequestBody FileInfo fileInfo) {
    FileInfo addedFile = fileService.setFile(fileInfo);
    log.info(String.valueOf(addedFile));
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{fileId}").buildAndExpand(addedFile.getId()).toUri();
    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("/files/{id}")
  public ResponseEntity<Void> deleteFile(@PathVariable Integer fileId) {
    return fileService.getFile(fileId).map(p -> {
      log.info("Attempting...");
      fileService.deleteFile(fileId);
      return new ResponseEntity<Void> (HttpStatus.OK);
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
