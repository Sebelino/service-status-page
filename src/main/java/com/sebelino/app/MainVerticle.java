package com.sebelino.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

    public static int PORT = 8888;

    @Override
    public void start() {
        Router router = Router.router(vertx);

        Handler handler = new Handler();

        router.get("/status").respond(context -> Future.succeededFuture(handler.dummyServicesPayload()));

        vertx.createHttpServer().requestHandler(router).listen(PORT).onSuccess(server -> System.out.println("HTTP server started on port " + server.actualPort()));
    }
}
