package com.manogna.phishingdetection.utils;

import java.util.ArrayList;
import com.manogna.phishingdetection.utils.DomainAgeChecker;
import java.util.List;
import com.manogna.phishingdetection.utils.UrlUtils;

public class UrlAnalyzer {

    public static AnalysisResult analyzeUrl(String url) {

        int score = 0;
        List<String> issues = new ArrayList<>();

        String lowerUrl = url.toLowerCase();
        String host = UrlUtils.extractHost(url);

        if (!lowerUrl.startsWith("https")) {
            score += 20;
            issues.add("URL not using HTTPS");
        }

        if (url.length() > 50) {
            score += 20;
            issues.add("Long URL");
        }

        if (url.contains("@")) {
            score += 30;
            issues.add("Contains '@' symbol");
        }

        if (url.contains("-")) {
            score += 10;
            issues.add("Hyphen in domain");
        }

        if (url.matches(".*\\d+\\.\\d+\\.\\d+\\.\\d+.*")) {
            score += 30;
            issues.add("IP address used instead of domain");
        }

        if (url.matches(".*\\d.*")) {
            score += 10;
            issues.add("Numbers in domain");
        }

        if (host.contains("login")) {
            score += 25;
            issues.add("Suspicious keyword: login");
        }

        if (lowerUrl.contains("verify")) {
            score += 25;
            issues.add("Suspicious keyword: verify");
        }

        if (lowerUrl.contains("account")) {
            score += 25;
            issues.add("Suspicious keyword: account");
        }

        if (lowerUrl.contains("secure")) {
            score += 25;
            issues.add("Suspicious keyword: secure");
        }

        if (lowerUrl.contains("update")) {
            score += 25;
            issues.add("Suspicious keyword: update");
        }

        int similarityScore = DomainSimilarityChecker.checkDomainSimilarity(url);
        if (similarityScore > 0) {
            score += similarityScore;
            issues.add("Possible typosquatting detected");
        }

        int brandScore = DomainSimilarityChecker.checkBrandImpersonation(url);
        if (brandScore > 0) {
            score += brandScore;
            issues.add("Brand impersonation detected");
        }

        // Domain age check
        int ageScore = DomainAgeChecker.checkDomainAge(host);
        if (ageScore > 0) {
            score += ageScore;
            issues.add("Recently registered domain (high phishing risk)");
        }

        return new AnalysisResult(score, issues);
    }
}