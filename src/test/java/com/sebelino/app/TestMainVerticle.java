package com.sebelino.app;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(VertxExtension.class)
public class TestMainVerticle {

    @Test
    @DisplayName("GET /status yields HTTP 200")
    @Timeout(value = 9, timeUnit = TimeUnit.SECONDS)
    void getAndCheckStatusCode(Vertx vertx, VertxTestContext testContext) {
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

    @Test
    @DisplayName("POST /status with required fields yields HTTP 201")
    @Timeout(value = 9, timeUnit = TimeUnit.SECONDS)
    void postAndCheckStatusCode(Vertx vertx, VertxTestContext testContext) {
        JsonObject payload = new JsonObject().put("name", "Google").put("url", "https://google.com");
        WebClient webClient = WebClient.create(vertx);
        vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> {
            webClient.post(MainVerticle.PORT, "localhost", "/status").as(BodyCodec.string())
                    .sendBuffer(payload.toBuffer(), testContext.succeeding(resp -> {
                        testContext.verify(() -> {
                            assertThat(resp.statusCode()).isEqualTo(201);
                            testContext.completeNow();
                        });
                    }));
        }));
    }
}
