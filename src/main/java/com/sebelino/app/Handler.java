package com.sebelino.app;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class Handler {

    public JsonObject get() {
        return dummyServicesPayload();
    }

    private JsonObject dummyServicesPayload() {
        JsonObject service = new JsonObject();
        service.put("name", "Facebook");
        service.put("url", "https://facebook.com");
        JsonArray services = new JsonArray().add(service);
        JsonObject status = new JsonObject().put("services", services);
        return status;
    }
}
