package com.sebelino.app;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.Tuple;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class Repository {

    private final MySQLPool pool;

    public Repository(Vertx vertx) {
        MySQLConnectOptions connectOptions = new MySQLConnectOptions()
                .setPort(3309)
                .setHost("localhost")
                .setDatabase("dev")
                .setUser("dev")
                .setPassword("secret");
        PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
        pool = MySQLPool.pool(vertx, connectOptions, poolOptions);
    }

    private static final Function<Row, Service> mapper = row -> {
        Service service = new Service();
        service.name = "Facebook";
        service.url = "https://facebook.com";
        return service;
    };

    public Future<List<Service>> findAll() {
        return pool.query("SELECT * FROM services")
                .execute()
                .map(rs -> StreamSupport.stream(rs.spliterator(), false)
                        .map(mapper)
                        .collect(Collectors.toList())
                );
    }

    public Future<RowSet<Row>> insertService(Service service) {
        return pool.preparedQuery("INSERT INTO services(name, url) VALUES (?, ?)")
                .execute(Tuple.of(service.name, service.url));
    }
}
