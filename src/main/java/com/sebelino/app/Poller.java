package com.sebelino.app;

import io.vertx.core.AbstractVerticle;

public class Poller extends AbstractVerticle {
    @Override
    public void start() {
        vertx.createHttpServer().requestHandler(
                req -> {
                    req.response()
                            .putHeader("content-type", "text/plain")
                            .end("Hello from vert.x!");
                }
        ).listen(8080);
    }
}
