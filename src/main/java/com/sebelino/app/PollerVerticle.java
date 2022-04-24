package com.sebelino.app;

import io.vertx.core.AbstractVerticle;

public class PollerVerticle extends AbstractVerticle {
    @Override
    public void start() {
        vertx.setPeriodic(1000, r -> {
            System.out.println("HOY");
        });
    }
}
