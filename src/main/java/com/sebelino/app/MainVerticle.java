package com.sebelino.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sebelino.app.repository.Repository;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.jackson.DatabindCodec;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class MainVerticle extends AbstractVerticle {

    public static int PORT = 8888;

    @Override
    public void start() {
        ObjectMapper mapper = DatabindCodec.mapper();
        mapper.registerModule(new JavaTimeModule());
        ObjectMapper prettyMapper = DatabindCodec.prettyMapper();
        prettyMapper.registerModule(new JavaTimeModule());

        Router router = Router.router(vertx);

        Repository repository = new Repository(vertx);

        Handler handler = new Handler(repository);

        router.get("/status").handler(handler::get);
        router.post("/status").handler(BodyHandler.create()).handler(handler::post);

        vertx.createHttpServer().requestHandler(router).listen(PORT).onSuccess(server -> System.out.println("HTTP server started on port " + server.actualPort()));

        DeploymentOptions pollerDeploymentOptions = new DeploymentOptions()
                .setWorker(true)
                .setWorkerPoolSize(20)
                .setWorkerPoolName("poller");
        vertx.deployVerticle(new PollerVerticle(), pollerDeploymentOptions);
    }
}
