package com.sebelino.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;

public class PollerVerticle extends AbstractVerticle {

    private WebClient client;

    @Override
    public void start() {
        client = WebClient.create(vertx);

        vertx.setPeriodic(3000, timerId -> handle());
    }

    private void handle() {
        SharedData sharedServiceStatuses = vertx.sharedData();
        LocalMap<String, String> serviceStatuses = sharedServiceStatuses.getLocalMap("service_statuses");
        System.out.printf("Polling %d services...%n", serviceStatuses.size());

        serviceStatuses.keySet().parallelStream().forEach(url -> pollService(url, serviceStatuses));

    }

    private void pollService(String url, LocalMap<String, String> serviceStatuses) {
        System.out.println("Polling url: " + url);
        client.requestAbs(HttpMethod.GET, url)
                .as(BodyCodec.string()).send(response -> {
                    if (response.succeeded()) {
                        System.out.println("Website is up");
                    } else {
                        System.out.println("Connection refused");
                    }
                });
    }
}
