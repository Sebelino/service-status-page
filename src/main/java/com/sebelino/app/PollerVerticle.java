package com.sebelino.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;

public class PollerVerticle extends AbstractVerticle {

    private WebClient client;

    @Override
    public void start() {
        client = WebClient.create(vertx);

        vertx.setPeriodic(3000, r -> {
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
