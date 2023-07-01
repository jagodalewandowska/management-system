package com.project.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import com.project.model.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
  public void init();

  public FileInfo save(MultipartFile file);

    public Resource load(String filename);

  public boolean delete(String filename);

  public void deleteAll();

  public Stream<Path> loadAll();
}
