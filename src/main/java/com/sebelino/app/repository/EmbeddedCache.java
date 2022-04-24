package com.sebelino.app.repository;

import java.util.HashMap;
import java.util.Map;

class EmbeddedCache {
    private final Map<String, String> urlToStatus = new HashMap<>();

    public void put(String url, String status) {
        urlToStatus.put(url, status);
    }

    public String get(String url) {
        return urlToStatus.get(url);
    }
}
