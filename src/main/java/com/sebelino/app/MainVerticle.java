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

        router.get("/status").respond(context -> Future.succeededFuture(dummyServicesPayload()));

        vertx.createHttpServer().requestHandler(router).listen(PORT).onSuccess(server -> System.out.println("HTTP server started on port " + server.actualPort()));
    }

    private static JsonObject dummyServicesPayload() {
        JsonObject service = new JsonObject();
        service.put("name", "Facebook");
        service.put("url", "https://facebook.com");
        JsonArray services = new JsonArray().add(service);
        JsonObject status = new JsonObject().put("services", services);
        return status;
    }
}
