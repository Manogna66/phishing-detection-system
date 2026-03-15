package com.manogna.phishingdetection.utils;

import org.apache.commons.net.whois.WhoisClient;
import java.io.IOException;

public class DomainAgeChecker {

    public static int checkDomainAge(String domain) {

        try {

            WhoisClient whois = new WhoisClient();
            whois.connect(WhoisClient.DEFAULT_HOST);

            String result = whois.query(domain);

            whois.disconnect();

            if (result.toLowerCase().contains("creation date")) {

                // simple heuristic
                if (result.contains("2025") || result.contains("2024")) {
                    return 25; // recently created
                }

            }

        } catch (IOException e) {
            System.out.println("WHOIS check failed");
        }

        return 0;
    }
}