package com.project.service;

import com.project.model.FileInfo;
import com.project.repository.FileRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public Page<FileInfo> getFiles(Pageable pageable) {
        return fileRepository.findAll(pageable);
    }

    @Override
    public Optional<FileInfo> getFile(Integer fileId) {
        return fileRepository.findById(fileId);
    }

    @Override
    public FileInfo setFile(@NotNull FileInfo fileInfo) {
        if (fileInfo.getName() != null) {
            FileInfo fileToSave = new FileInfo(fileInfo.getName(), fileInfo.getUrl(), fileInfo.getProjekt());
            return fileRepository.save(fileToSave);
        } else {
            String newName = fileInfo.getUrl().replace("/uploads/", "");
            FileInfo fileToSave = new FileInfo(newName, fileInfo.getUrl(), fileInfo.getProjekt());

            return fileRepository.save(fileToSave);
        }
    }

    @Override
    @Transactional
    public void deleteFile(Integer fileId) {
        fileRepository.deleteById(fileId);
    }
}
