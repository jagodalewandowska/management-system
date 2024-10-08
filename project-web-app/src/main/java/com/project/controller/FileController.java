package com.project.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.project.model.Projekt;
import com.project.model.Zadanie;
import com.project.service.ProjektService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.model.FileInfo;
import com.project.service.FilesStorageService;

@Controller
@RequestMapping("/app")
@Slf4j
public class FileController {

  FilesStorageService storageService;

  private ProjektService projektService;

  public FileController(ProjektService projektService, FilesStorageService storageService) {
    this.storageService = storageService;
    this.projektService = projektService;
  }

  @GetMapping("/files/new")
  public String newFile(Model model, @RequestParam("projektId") Integer projektId, Pageable pageable) {
    model.addAttribute("projektId", projektId);
    return "upload_form";
  }

  @PostMapping(path = "/files/upload")
  public String uploadFile(@ModelAttribute @Valid FileInfo fileInfo, Model model, @RequestParam("file") MultipartFile file,
                           @RequestParam("projektId") Integer projektId) {
    String message = "";

    try {

      model.addAttribute("projektId", projektId);

      String originalFilename = file.getOriginalFilename();
      String filename = originalFilename.substring(originalFilename.lastIndexOf("/") + 1);

      fileInfo.setName(filename);
      fileInfo.setUrl("/uploads/" + filename);

      Projekt projekt = new Projekt();
      projekt.setProjektId(projektId);
      fileInfo.setProjekt(projekt);

      storageService.save(file, fileInfo);
      return "redirect:/app/files?projektId=" + projektId;
    } catch (Exception e) {
      message = "Nie można było dodać pliku: " + file.getOriginalFilename() + ". Błąd: " + e.getMessage();
      model.addAttribute("message", message);
    }

    return "upload_form";
  }

  @GetMapping("/doPobrania")
  public String doPobrania(Pageable pageable, Model model) {
    model.addAttribute("filesDB", storageService.getFileInfos(pageable));
    return "allFiles";
  }

  @GetMapping("/files")
  public String getListFiles(@RequestParam(required = false) Integer projektId,
                             Pageable pageable, Model model) {
    model.addAttribute("filesDB", storageService.getFileInfos(pageable));
    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
      String filename = path.getFileName().toString();
      String url = MvcUriComponentsBuilder
          .fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();
      Projekt projekt;
      projekt = projektService.getProjekt(projektId).get();

      return new FileInfo(filename, url, projekt);
    }).collect(Collectors.toList());
    model.addAttribute("files", fileInfos);
    model.addAttribute("projektId", projektId);
    return "files";
  }

  @GetMapping("/files/{filename:.+}")
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.load(filename);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  @PostMapping("/files/delete/{name}/{fileId}/{projektId}")
  public String fileDelete(@ModelAttribute FileInfo fileInfo, @PathVariable String name,
                           @PathVariable Integer projektId) throws IOException {
    storageService.delete(name, fileInfo.getFileId());
    return "redirect:/app/files?projektId=" + projektId;
  }
}
