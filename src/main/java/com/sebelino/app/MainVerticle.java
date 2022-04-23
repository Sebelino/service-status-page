package com.sebelino.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {

    public static int PORT = 8888;

    @Override
    public void start() {

        Router router = Router.router(vertx);

        Repository repository = new Repository(vertx);

        Handler handler = new Handler(repository);

        router.get("/status").handler(handler::get);
        router.post("/status").handler(BodyHandler.create()).handler(handler::post);

        vertx.createHttpServer().requestHandler(router).listen(PORT).onSuccess(server -> System.out.println("HTTP server started on port " + server.actualPort()));
    }
}
