package com.project.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.model.FileInfo;
import com.project.service.FilesStorageService;

@Controller
public class FileController {

  @Autowired
  FilesStorageService storageService;

  @GetMapping("/files/new")
  public String newFile(Model model) {
    return "upload_form";
  }

  @PostMapping("/files/upload")
  public String uploadFile(Model model, @RequestParam("file") MultipartFile file) {
    String message = "";

    try {
      storageService.save(file);

//      message = "Uploaded the file successfully: " + file.getOriginalFilename();
//      model.addAttribute("message", message);
      return "redirect:/files";
    } catch (Exception e) {
      message = "Nie można było dodać pliku: " + file.getOriginalFilename() + ". Błąd: " + e.getMessage();
      model.addAttribute("message", message);
    }

    return "upload_form";
  }

  @GetMapping("/files")
  public String getListFiles(Model model) {
    List<FileInfo> fileInfos = storageService.loadAll().map(path -> {
      String filename = path.getFileName().toString();
      String url = MvcUriComponentsBuilder
          .fromMethodName(FileController.class, "getFile", path.getFileName().toString()).build().toString();

      return new FileInfo(filename, url);
    }).collect(Collectors.toList());

    model.addAttribute("files", fileInfos);

    return "files";
  }

  @GetMapping("/files/{filename:.+}")
  public ResponseEntity<Resource> getFile(@PathVariable String filename) {
    Resource file = storageService.load(filename);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  @GetMapping("/files/delete/{filename:.+}")
  public String deleteFile(@PathVariable String filename, Model model, RedirectAttributes redirectAttributes) {
    try {
      boolean existed = storageService.delete(filename);

      if (existed) {
        redirectAttributes.addFlashAttribute("message", "Usunięto plik: " + filename);
      } else {
        redirectAttributes.addFlashAttribute("message", "Plik nie istnieje!");
      }
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("message",
          "Nie można usunąć pliku: " + filename + ". Błąd: " + e.getMessage());
    }

    return "redirect:/files";
  }
}
