package com.project.service;

import com.project.model.Student;
import com.project.model.Tutor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;


@Service
public class TutorServiceImpl implements TutorService {

    @Value("http://localhost:8080")
    private String  serverUrl;

    private final static String RESOURCE_PATH = "/api/tutors";
    private RestTemplate restTemplate;
    @Override
    public Optional<Tutor> getTutor(Integer tutorId) {
        URI url = ServiceUtil.getUriComponent(serverUrl, getResourcePath(tutorId))
                .build()
                .toUri();
        return Optional.ofNullable(restTemplate.getForObject(url, Tutor.class));

    }

    @Override
    public Tutor setTutor(Tutor tutor) {
        if (tutor.getTutorId() != null) {
            String url = getUriStringComponent(tutor.getTutorId());
            restTemplate.put(url, tutor);
            return tutor;
        } else {
            HttpEntity<Tutor> request = new HttpEntity<>(tutor);
            String url = getUriStringComponent();
            URI location = restTemplate.postForLocation(url, request);
            return restTemplate.getForObject(location, Tutor.class);
        }
    }

    @Override
    public Page<Tutor> getTutors(Pageable pageable) {
        URI url = ServiceUtil.getURI(serverUrl, getResourcePath(), pageable);
        return getPage(url, restTemplate);
    }

    private String getUriStringComponent() {
        return serverUrl + getResourcePath();
    }

    private String getResourcePath() {
        return RESOURCE_PATH;
    }

    private String getUriStringComponent(Integer id) {
        return serverUrl + getResourcePath(id);
    }


    private Page<Tutor> getPage(URI uri, RestTemplate restTemplate) {
        return ServiceUtil.getPage(uri, restTemplate,
                new ParameterizedTypeReference<RestResponsePage<Tutor>>() {});
    }

    private String getResourcePath(Integer id) {
        return RESOURCE_PATH + "/" + id;
    }

}