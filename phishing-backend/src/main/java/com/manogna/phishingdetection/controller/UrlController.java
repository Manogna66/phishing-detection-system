package com.manogna.phishingdetection.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.manogna.phishingdetection.model.UrlRequest;
import com.manogna.phishingdetection.model.UrlResponse;
import com.manogna.phishingdetection.utils.AnalysisResult;
import com.manogna.phishingdetection.utils.UrlAnalyzer;
import com.manogna.phishingdetection.service.VirusTotalService;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "*")
@RestController
public class UrlController {

    private final VirusTotalService virusTotalService;

    // Constructor Injection
    public UrlController(VirusTotalService virusTotalService) {
        this.virusTotalService = virusTotalService;
    }

    @PostMapping("/check-url")
    public UrlResponse checkUrl(@RequestBody UrlRequest request) {

        String url = request.getUrl();

        AnalysisResult result = UrlAnalyzer.analyzeUrl(url);

        int riskScore = result.getRiskScore();

        boolean virusTotalFlag = virusTotalService.checkUrlReputation(url);

        if (virusTotalFlag) {
            riskScore += 50;
            result.getIssues().add("Flagged by VirusTotal");
        }

        boolean phishing = riskScore >= 40;

        return new UrlResponse(url, phishing, riskScore, result.getIssues());
    }
}