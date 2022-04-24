package com.sebelino.app;

import com.sebelino.app.repository.EmbeddedStorage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.VertxException;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;

public class PollerVerticle extends AbstractVerticle {

    private WebClient client;

    @Override
    public void start() {
        client = WebClient.create(vertx);

        vertx.setPeriodic(4000, timerId -> pollAllServices());
    }

    private void pollAllServices() {
        EmbeddedStorage embeddedStorage = new EmbeddedStorage(vertx.sharedData());
        System.out.printf("Polling %d services...%n", embeddedStorage.keySet().size());

        embeddedStorage.keySet().parallelStream().forEach(url -> pollService(url, embeddedStorage));
    }

    private void pollService(String url, EmbeddedStorage serviceStatuses) {
        try {
            client.requestAbs(HttpMethod.GET, url).as(BodyCodec.string()).send(response -> {
                if (response.succeeded()) {
                    serviceStatuses.put(url, "OK");
                } else {
                    serviceStatuses.put(url, "FAIL");
                }
            });
        } catch (VertxException e) {
            // Likely MalformedUrlException
            serviceStatuses.put(url, "FAIL");
        }
    }
}
