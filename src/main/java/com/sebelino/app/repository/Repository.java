package com.sebelino.app.repository;

import com.sebelino.app.Service;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;

import java.util.List;

public class Repository {

    private final Database database;

    public Repository(Vertx vertx) {
        database = new Database(vertx);
    }

    public Future<List<Service>> findAll() {
        return database.findAll();
    }

    public Future<RowSet<Row>> insertService(Service service) {
        return database.insertService(service);
    }
}
