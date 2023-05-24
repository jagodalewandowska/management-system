package com.project.auth;

import com.project.model.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j // tworzenie loggera
public class AuthServiceImpl implements AuthService {
    private final RestTemplate restTemplate; // alternatywa -> webclient spring'a, przeznaczony dla webflux (reaktywne podejście)
                                            // może być używane ze zwykłym restem

    @Value("${rest.server.url}")
    private String serverUrl;

    private String getUriStringComponent(String path) {
        return serverUrl + path;
    }

    @Override
    public Optional<String> login(Credentials credentials) {
        HttpEntity<Credentials> request = new HttpEntity<>(credentials);
        String url = getUriStringComponent("/api/login");
        log.info("Request -> Post {},", url);
        AuthResponse authResponse = restTemplate.postForObject(url, request, AuthResponse.class);
        return Optional.ofNullable(authResponse.getToken());
    }

    @Override
    public void register(Student student) {
        HttpEntity<Student> request = new HttpEntity<>(student);
        String url = getUriStringComponent("/api/register");
        log.info("Request -> Post {}", url);
        restTemplate.postForObject(url, request, Void.class);
    }
}
