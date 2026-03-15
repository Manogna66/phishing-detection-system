package com.manogna.phishingdetection.utils;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class DomainSimilarityChecker {

    private static final List<String> trustedDomains = Arrays.asList(
            "google",
            "paypal",
            "amazon",
            "microsoft",
            "facebook",
            "apple",
            "netflix",
            "bankofamerica"
    );

    public static int checkDomainSimilarity(String url) {

        String domain = extractMainDomain(url);

        for (String trusted : trustedDomains) {

            int distance = levenshteinDistance(domain, trusted);

            if (distance == 1) {
                return 50;
            }

            if (distance == 2) {
                return 30;
            }
        }

        return 0;
    }

    public static int checkBrandImpersonation(String url) {

        String lowerUrl = url.toLowerCase();

        String realDomain = extractRealDomain(url);
        String mainDomain = extractMainDomain(url);

        for (String brand : trustedDomains) {

            // Brand appears somewhere but is not the real domain
            if (lowerUrl.contains(brand) && !realDomain.contains(brand)) {
                return 40;
            }

            // Brand appears inside a longer domain like amazon-login-security
            if (mainDomain.contains(brand) && !mainDomain.equals(brand)) {
                return 40;
            }
        }

        return 0;
    }

    private static String extractRealDomain(String url) {

        try {

            URI uri = new URI(url);
            String host = uri.getHost();

            if (host == null) return "";

            String[] parts = host.split("\\.");

            if (parts.length >= 2) {

                return parts[parts.length - 2] + "." + parts[parts.length - 1];
            }

            return host;

        } catch (Exception e) {
            return "";
        }
    }

    private static String extractMainDomain(String url) {

        String realDomain = extractRealDomain(url);

        if (realDomain.contains(".")) {
            return realDomain.split("\\.")[0];
        }

        return realDomain;
    }

    private static int levenshteinDistance(String a, String b) {

        int[][] dp = new int[a.length() + 1][b.length() + 1];

        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {

                int cost = a.charAt(i - 1) == b.charAt(j - 1) ? 0 : 1;

                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost
                );
            }
        }

        return dp[a.length()][b.length()];
    }
}