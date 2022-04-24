package com.sebelino.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import org.apache.commons.lang3.RandomStringUtils;

public class PollerVerticle extends AbstractVerticle {

    private WebClient client;

    @Override
    public void start() {
        SharedData sharedServiceStatuses = vertx.sharedData();
        LocalMap<String, String> serviceStatuses = sharedServiceStatuses.getLocalMap("service_statuses");

        client = WebClient.create(vertx);

        vertx.setPeriodic(3000, r -> {
            System.out.println("Shared: " + serviceStatuses.get("hoy"));
            client.get(80, "google.com", "/")
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
