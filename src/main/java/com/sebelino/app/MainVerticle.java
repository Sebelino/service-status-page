package com.sebelino.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

    public static int PORT = 8888;

    @Override
    public void start() {
        Router router = Router.router(vertx);

        Handler handler = new Handler();

        router.get("/status").respond(context -> Future.succeededFuture(handler.get()));

        vertx.createHttpServer().requestHandler(router).listen(PORT).onSuccess(server -> System.out.println("HTTP server started on port " + server.actualPort()));
    }
}
