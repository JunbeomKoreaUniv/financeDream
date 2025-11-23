package com.finance.demo.controller;

import com.finance.demo.dto.NewsRequest;
import com.finance.demo.dto.NewsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/api/events")
public class NewsController {

    private final RestTemplate restTemplate;
    private static final String API_KEY = "e5558661-9c9e-44eb-8a39-06015d7a12d0";
    private static final String API_URL = "https://eventregistry.org/api/v1/event/getEvents";

    @Autowired
    public NewsController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/search")
    public NewsResponse getEvents(@RequestBody NewsRequest request) {
        request.setApiKey(API_KEY);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NewsRequest> entity = new HttpEntity<>(request, headers);

        return restTemplate.postForObject(API_URL, entity, NewsResponse.class);
    }
}
