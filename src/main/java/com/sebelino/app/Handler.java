package com.sebelino.app;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.StringUtils;

public class Handler {

    Repository repository;

    public Handler(Repository repository) {
        this.repository = repository;
    }

    public void get(RoutingContext context) {
        repository.findAll().onSuccess(
                services -> context.response().end(Json.encode(Status.of(services)))
        ).onFailure(
                throwable -> context.fail(500, throwable)
        );
    }

    public void post(RoutingContext context) {
        Service service = context.getBodyAsJson().mapTo(Service.class);
        if (StringUtils.isEmpty(service.name)) {
            JsonObject payload = new JsonObject().put("error", "Missing field: name");
            context.response().setStatusCode(400).end(Json.encode(payload));
            return;
        }
        if (StringUtils.isEmpty(service.url)) {
            JsonObject payload = new JsonObject().put("error", "Missing field: url");
            context.response().setStatusCode(400).end(Json.encode(payload));
            return;
        }
        System.out.printf("Service: %s %s%n", service.name, service.url);
        context.response().setStatusCode(201).end();
    }
}
