package com.sebelino.app;

import io.vertx.core.Vertx;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {

    @Test
    @Timeout(value = 9, timeUnit = TimeUnit.SECONDS)
    void http_server_check_response(Vertx vertx, VertxTestContext testContext) {
        WebClient webClient = WebClient.create(vertx);
        vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> {
            webClient.get(MainVerticle.PORT, "localhost", "/status").as(BodyCodec.string()).send(testContext.succeeding(resp -> {
                testContext.verify(() -> {
                    assertThat(resp.statusCode()).isEqualTo(200);
                    testContext.completeNow();
                });
            }));
        }));
    }
}
