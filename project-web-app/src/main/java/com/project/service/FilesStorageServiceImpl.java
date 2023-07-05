package com.project.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import com.project.model.FileInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class FilesStorageServiceImpl implements FilesStorageService {
  private final Path root = Paths.get("./uploads");
  private RestTemplate restTemplate;

  private ProjektService projektService;


  @Value("http://localhost:8080")
  private String  serverUrl;

  private final static String RESOURCE_PATH = "/api/files";

  public FilesStorageServiceImpl(RestTemplate restTemplate, ProjektService projektService) {
    this.restTemplate = restTemplate;
    this.projektService = projektService;
  }

  @Override
  public void init() {
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      throw new RuntimeException("Nie można było zainicjalizować folderu do dodawania plików!");
    }
  }

  @Override
  public FileInfo save(MultipartFile file, FileInfo fileInfo) {
    try {
      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));

      HttpEntity<FileInfo> request = new HttpEntity<>(fileInfo);
      String url = getUriStringComponent();
      log.info("REQUEST -> POST {}", url);
      URI location = restTemplate.postForLocation(url, request);
      log.info("REQUEST (location) -> GET {}", location);

      return restTemplate.getForObject(location, FileInfo.class);
    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("Plik o takiej nazwie już istnieje.");
      }

      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  public Resource load(String filename) {
    try {
      Path file = root.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Nie można było odczytać pliku!");
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Błąd: " + e.getMessage());
    }
  }

  @Override
  public void delete(String name, Integer fileId) throws IOException {
    Path file = root.resolve(name);
    Files.deleteIfExists(file);
    URI url = ServiceUtil.getUriComponent(serverUrl, getResourcePath(fileId))
              .build()
              .toUri();
      log.info("REQUEST -> DELETE {}", url);
      restTemplate.delete(url);
  }

  @Override
  public void deleteAll() {
    FileSystemUtils.deleteRecursively(root.toFile());
  }

  @Override
  public Page<FileInfo> getFileInfos(Pageable pageable) {
    URI url = ServiceUtil.getURI(serverUrl, getResourcePath(), pageable);
    log.info("REQUEST -> GET {}", url);
    return getPage(url, restTemplate);
  }

  @Override
  public Stream<Path> loadAll() {
    try {

      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
    } catch (IOException e) {
      throw new RuntimeException("Nie można było załadować plików!");
    }
  }


  private String getResourcePath() {
    return RESOURCE_PATH;
  }

  private String getResourcePath(Integer id) {
    return RESOURCE_PATH + "/" + id;
  }

  private String getProjectPath(Integer id) {
    return "/" + id;
  }

  private Page<FileInfo> getPage(URI uri, RestTemplate restTemplate) {
    return ServiceUtil.getPage(uri, restTemplate,
            new ParameterizedTypeReference<RestResponsePage<FileInfo>>() {});
  }

  private String getUriStringComponent() {
    return serverUrl + getResourcePath();
  }

  private String getUriStringComponent(Integer id) {
    return serverUrl + getResourcePath(id);
  }

}
