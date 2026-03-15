package com.manogna.phishingdetection.model;

import java.util.List;

public class UrlResponse {

    private String url;
    private boolean phishing;
    private int riskScore;
    private List<String> detectedIssues;

    public UrlResponse(String url, boolean phishing, int riskScore, List<String> detectedIssues) {
        this.url = url;
        this.phishing = phishing;
        this.riskScore = riskScore;
        this.detectedIssues = detectedIssues;
    }

    public String getUrl() {
        return url;
    }

    public boolean isPhishing() {
        return phishing;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public List<String> getDetectedIssues() {
        return detectedIssues;
    }
}