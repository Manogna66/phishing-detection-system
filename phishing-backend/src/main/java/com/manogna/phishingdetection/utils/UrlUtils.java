package com.manogna.phishingdetection.utils;

import java.net.URI;

public class UrlUtils {

    public static String extractHost(String url) {

        try {

            URI uri = new URI(url);

            String host = uri.getHost();

            if (host == null) {
                return "";
            }

            return host.toLowerCase();

        } catch (Exception e) {

            return "";
        }
    }
}