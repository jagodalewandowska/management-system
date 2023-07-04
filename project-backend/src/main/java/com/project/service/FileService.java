package com.project.service;

import com.project.model.FileInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FileService {

    Page<FileInfo> getFiles(Pageable pageable);

    Optional<FileInfo> getFile(Integer fileId);

    FileInfo setFile(FileInfo fileInfo);

    @Transactional
    void deleteFile(Integer fileId);
}
