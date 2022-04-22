package com.sebelino.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        Router router = Router.router(vertx);

        router.route().handler(context -> {
            String address = context.request().connection().remoteAddress().toString();
            MultiMap queryParams = context.queryParams();
            String name = queryParams.contains("name") ? queryParams.get("name") : "unknown";
            JsonObject json = new JsonObject().put("name", name).put("address", address).put("message", "Hello " + name + " connected from " + address);
            context.json(json);
        });

        vertx.createHttpServer().requestHandler(router).listen(8888).onSuccess(server -> System.out.println("HTTP server started on port " + server.actualPort()));
    }
}
