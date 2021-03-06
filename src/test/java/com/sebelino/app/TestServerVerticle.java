package com.sebelino.app;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import io.vertx.junit5.Timeout;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(VertxExtension.class)
public class TestServerVerticle {

    @Test
    @DisplayName("GET /status yields HTTP 200")
    @Timeout(value = 9, timeUnit = TimeUnit.SECONDS)
    void getAndCheckStatusCode(Vertx vertx, VertxTestContext testContext) {
        WebClient webClient = WebClient.create(vertx);
        vertx.deployVerticle(new ServerVerticle(), testContext.succeeding(id -> {
            webClient.get(ServerVerticle.PORT, "localhost", "/status").as(BodyCodec.string()).send(testContext.succeeding(resp -> {
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
        String serviceName = RandomStringUtils.randomAlphabetic(10);
        String serviceUrl = String.format("https://%s.com", serviceName);
        JsonObject payload = new JsonObject().put("name", serviceName).put("url", serviceUrl);
        WebClient webClient = WebClient.create(vertx);
        vertx.deployVerticle(new ServerVerticle(), testContext.succeeding(id -> {
            webClient.post(ServerVerticle.PORT, "localhost", "/status").as(BodyCodec.string()).sendBuffer(payload.toBuffer(), testContext.succeeding(resp -> {
                testContext.verify(() -> {
                    assertThat(resp.statusCode()).isEqualTo(201);
                    testContext.completeNow();
                });
            }));
        }));
    }

    @Test
    @DisplayName("POST /status successfully, then GET it")
    @Timeout(value = 9, timeUnit = TimeUnit.SECONDS)
    void postAndGet(Vertx vertx, VertxTestContext testContext) {
        String serviceName = RandomStringUtils.randomAlphabetic(10);
        String serviceUrl = String.format("https://%s.com", serviceName);
        JsonObject payload = new JsonObject().put("name", serviceName).put("url", serviceUrl);
        WebClient webClient = WebClient.create(vertx);
        vertx.deployVerticle(new MainVerticle(), testContext.succeeding(id -> {
            webClient.post(ServerVerticle.PORT, "localhost", "/status").as(BodyCodec.string()).sendBuffer(payload.toBuffer(), testContext.succeeding(resp -> {
                testContext.verify(() -> {
                    assertThat(resp.statusCode()).isEqualTo(201);
                    testContext.completeNow();
                });
            }));
            webClient.get(ServerVerticle.PORT, "localhost", "/status").as(BodyCodec.string()).sendBuffer(payload.toBuffer(), testContext.succeeding(resp -> {
                testContext.verify(() -> {
                    assertThat(resp.statusCode()).isEqualTo(200);
                    JsonObject body = new JsonObject(resp.body());
                    ServiceSet status = body.mapTo(ServiceSet.class);
                    Service retrievedService = status.services.stream().filter(s -> s.name.equals(serviceName)).findAny().orElseThrow();
                    assertThat(retrievedService.url).isEqualTo(serviceUrl);
                    testContext.completeNow();
                });
            }));
        }));
    }
}
