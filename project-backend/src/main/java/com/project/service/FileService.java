package com.project.service;

import com.project.model.FileInfo;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface FileService {

    List<FileInfo> getFiles();

    Optional<FileInfo> getFile(Integer fileId);

    FileInfo setFile(FileInfo fileInfo);

    @Transactional
    void deleteFile(Integer fileId);
}
