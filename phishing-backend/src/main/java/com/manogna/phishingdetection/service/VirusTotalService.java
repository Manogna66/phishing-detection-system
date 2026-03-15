package com.manogna.phishingdetection.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class VirusTotalService {

    private final String API_KEY = "80270d405063119da00100a2e7db9d1b59121695e91ead4a553376d41e35462b";

    public boolean checkUrlReputation(String url) {

        String apiUrl = "https://www.virustotal.com/api/v3/urls";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-apikey", API_KEY);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("url", url);

        HttpEntity<MultiValueMap<String, String>> entity =
                new HttpEntity<>(body, headers);

        try {

            ResponseEntity<String> response =
                    restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

            String responseBody = response.getBody();

            if (responseBody != null && responseBody.contains("malicious")) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("VirusTotal check failed: " + e.getMessage());
        }

        return false;
    }
}