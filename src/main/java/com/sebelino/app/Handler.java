package com.sebelino.app;

import com.sebelino.app.repository.Repository;
import io.vertx.core.Vertx;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.ext.web.RoutingContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

public class Handler {

    Vertx vertx;
    Repository repository;

    public Handler(Vertx vertx, Repository repository) {
        this.vertx = vertx;
        this.repository = repository;
    }

    public void get(RoutingContext context) {
        SharedData sharedServiceStatuses = vertx.sharedData();
        LocalMap<String, String> serviceStatuses = sharedServiceStatuses.getLocalMap("service_statuses");

        repository.findAll().onSuccess(
                services -> {
                    for (Service service : services) {
                        serviceStatuses.put(service.url, "UNKNOWN");
                    }
                    context.response().end(Json.encodePrettily(Status.of(services)));
                }
        ).onFailure(
                throwable -> context.fail(500, throwable)
        );
    }

    public void post(RoutingContext context) {
        JsonObject body = context.getBodyAsJson();
        if (body == null) {
            JsonObject payload = new JsonObject().put("error", "Missing JSON body");
            context.response().setStatusCode(400).end(Json.encode(payload));
            return;
        }
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
        repository.insertService(service)
                .onSuccess(
                        rows -> context.response().setStatusCode(201).end()
                )
                .onFailure(
                        throwable -> context.fail(500, throwable)
                );
    }
}
