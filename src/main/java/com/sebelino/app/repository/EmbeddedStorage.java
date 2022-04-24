package com.sebelino.app.repository;

import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;

import java.util.Set;

public class EmbeddedStorage {

    private final LocalMap<String, String> map;

    public EmbeddedStorage(SharedData sharedData) {
        map = sharedData.getLocalMap("service_statuses");
    }

    public void put(String key, String value) {
        map.put(key, value);
    }

    public String get(String key) {
        return map.get(key);
    }

    public boolean containsKey(String key) {
        return map.containsKey(key);
    }

    public Set<String> keySet() {
        return map.keySet();
    }
}
