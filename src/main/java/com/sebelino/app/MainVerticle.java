package com.sebelino.app;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        vertx.deployVerticle(new ServerVerticle());

        DeploymentOptions pollerDeploymentOptions = new DeploymentOptions()
                .setWorker(true)
                .setWorkerPoolSize(20)
                .setWorkerPoolName("poller");
        vertx.deployVerticle(new PollerVerticle(), pollerDeploymentOptions);
    }
}
