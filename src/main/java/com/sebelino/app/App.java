package com.sebelino.app;

import io.vertx.core.Vertx;

public class App {
    public static void main(String[] args) {
        System.out.println("Starting Vert.x server...");
        Vertx.vertx().deployVerticle(new Poller());
    }
}
