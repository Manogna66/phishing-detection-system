package com.manogna.phishingdetection.utils;

import java.util.List;

public class AnalysisResult {

    private int riskScore;
    private List<String> issues;

    public AnalysisResult(int riskScore, List<String> issues) {
        this.riskScore = riskScore;
        this.issues = issues;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public List<String> getIssues() {
        return issues;
    }
}