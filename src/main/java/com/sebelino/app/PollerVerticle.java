package com.sebelino.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;

public class PollerVerticle extends AbstractVerticle {

    private WebClient client;

    @Override
    public void start() {
        SharedData sharedServiceStatuses = vertx.sharedData();
        LocalMap<String, String> serviceStatuses = sharedServiceStatuses.getLocalMap("service_statuses");

        client = WebClient.create(vertx);

        vertx.setPeriodic(3000, r -> {
            String serviceHostname = "google.com";
            client.get(80, serviceHostname, "/")
                    .as(BodyCodec.string()).send(response -> {
                        if (response.succeeded()) {
                            System.out.println("Website is up");
                        } else {
                            System.out.println("Connection refused");
                        }
                    });
        });
    }
}
