package com.sebelino.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        Router router = Router.router(vertx);

        Route route = router.get("/status");

        route.handler(context -> context.json(dummyServicesPayload()));

        vertx.createHttpServer().requestHandler(router).listen(8888).onSuccess(server -> System.out.println("HTTP server started on port " + server.actualPort()));
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
