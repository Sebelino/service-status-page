package com.sebelino.app;

import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;

public class Handler {

    Repository repository;

    public Handler(Repository repository) {
        this.repository = repository;
    }

    public void get(RoutingContext context) {
        repository.findAll().onSuccess(
                data -> context.response().end(Json.encode(data))
        ).onFailure(
                throwable -> context.fail(500, throwable)
        );
    }
}
