package com.project.service;

import com.project.model.Projekt;
import com.project.model.Student;
import com.project.model.User;
import com.project.model.UserRegistrationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(ProjektServiceImpl.class);

    @Value("http://localhost:8080")
    private String  serverUrl;
    private final static String RESOURCE_PATH = "/api/users";
    private RestTemplate restTemplate;
    @Autowired
    public UserServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private String getResourcePath() {
        return RESOURCE_PATH;
    }
    private Page<User> getPage(URI uri, RestTemplate restTemplate) {
        return ServiceUtil.getPage(uri, restTemplate,
                new ParameterizedTypeReference<RestResponsePage<User>>() {});
    }
    @Override
    public Page<User> getAll(Pageable pageable) {
        URI url = ServiceUtil.getURI(serverUrl, getResourcePath(), pageable);
        return getPage(url, restTemplate);
    }

    //@Override
    public User save(UserRegistrationDto userRegistrationDto) {
        //todo
        return null;
    }
}
