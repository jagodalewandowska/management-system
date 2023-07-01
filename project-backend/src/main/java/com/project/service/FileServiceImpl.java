package com.project.service;

import com.project.model.FileInfo;
import com.project.repository.FileRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;

    @Override
    public List<FileInfo> getFiles() {
        return fileRepository.findAll();
    }

    @Override
    public Optional<FileInfo> getFile(Integer fileId) {
        return fileRepository.findById(fileId);
    }

    @Override
    public FileInfo setFile(FileInfo fileInfo) {
        if (fileInfo.getName() != null) {
            FileInfo fileToSave = new FileInfo(fileInfo.getName(), fileInfo.getUrl());

            return fileRepository.save(fileToSave);
        } else {
            String newName = fileInfo.getUrl().replace("/uploads/", "");
            FileInfo fileToSave = new FileInfo(newName, fileInfo.getUrl());

            return fileRepository.save(fileToSave);
        }

    }

    @Override
    @Transactional
    public void deleteFile(Integer fileId) {
        fileRepository.deleteById(fileId);
    }

}
