package com.project.service;

import java.net.URI;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
public class ServiceUtil {
    public static <T> RestResponsePage<T> getPage(URI uri, RestTemplate restTemplate,
                                                  ParameterizedTypeReference<RestResponsePage<T>> responseType) {
        ResponseEntity<RestResponsePage<T>> result = restTemplate.exchange(uri, HttpMethod.GET, null,
                responseType);
        return result.getBody();
    }
    public static URI getURI(String serverUrl, String resourcePath, Pageable pageable) {
        return getUriComponent(serverUrl, resourcePath)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .queryParam("sort", ServiceUtil.getSortParams(pageable.getSort())).build().toUri();
    }
    public static UriComponentsBuilder getUriComponent(String serverUrl, String resourcePath, Pageable
            pageable) {
        return getUriComponent(serverUrl, resourcePath)
                .queryParam("page", pageable.getPageNumber())
                .queryParam("size", pageable.getPageSize())
                .queryParam("sort", ServiceUtil.getSortParams(pageable.getSort()));
    }
    public static UriComponentsBuilder getUriComponent(String serverUrl, String resourcePath) {
        return UriComponentsBuilder.fromUriString(serverUrl).path(resourcePath);
    }
    public static String getSortParams(Sort sort) {
        StringBuilder builder = new StringBuilder();
        if (sort != null) {
            String sep = "";
            for (Sort.Order order : sort) {
                builder.append(sep).append(order.getProperty()).append(",").append(order.getDirection());
                sep = "&sort=";
            }
        }
        return builder.toString();
    }
}