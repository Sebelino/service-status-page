package com.sebelino.app.repository;

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

class Database {

    private final MySQLPool pool;

    public Database(Vertx vertx) {
        MySQLConnectOptions connectOptions = new MySQLConnectOptions().setPort(3309).setHost("localhost").setDatabase("dev").setUser("dev").setPassword("secret");
        PoolOptions poolOptions = new PoolOptions().setMaxSize(5);
        pool = MySQLPool.pool(vertx, connectOptions, poolOptions);
    }

    private static final Function<Row, ServiceEntity> mapper = row -> {
        ServiceEntity service = new ServiceEntity();
        service.name = row.getString("name");
        service.url = row.getString("url");
        service.createdAt = row.getLocalDateTime("created_at");
        return service;
    };

    public Future<List<ServiceEntity>> findAll() {
        return pool
                .query("SELECT * FROM services")
                .execute()
                .map(rs -> StreamSupport.stream(rs.spliterator(), false).map(mapper).collect(Collectors.toList()));
    }

    public Future<RowSet<Row>> insertService(ServiceEntity service) {
        return pool.preparedQuery("INSERT INTO services(name, url) VALUES (?, ?)").execute(Tuple.of(service.name, service.url));
    }
}
