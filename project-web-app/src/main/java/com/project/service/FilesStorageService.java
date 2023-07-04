package com.project.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import com.project.model.FileInfo;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
  public void init();

  public FileInfo save(MultipartFile file, FileInfo fileInfo);

  Page<FileInfo> getFileInfos(Pageable pageable);

  public Resource load(String filename);

  public boolean delete(String filename, Integer fileId);

  public void deleteAll();

  public Stream<Path> loadAll();
}
