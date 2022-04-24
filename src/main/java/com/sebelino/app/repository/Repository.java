package com.sebelino.app.repository;

import com.sebelino.app.Service;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;

import java.util.List;
import java.util.stream.Collectors;

public class Repository {

    private final Database database;
    private final SharedData sharedData;

    public Repository(Vertx vertx) {
        database = new Database(vertx);
        sharedData = vertx.sharedData();
    }

    public Future<List<Service>> findAll() {
        Future<List<ServiceEntity>> entitiesFuture = database.findAll();
        Future<List<Service>> servicesFuture = entitiesFuture.map(entities -> entities.stream().map(ServiceEntity::toModel).collect(Collectors.toList()));
        servicesFuture.map(services -> services.stream().map(this::addStatus).collect(Collectors.toList()));
        return servicesFuture;
    }

    private Service addStatus(Service service) {
        LocalMap<String, String> serviceStatuses = sharedData.getLocalMap("service_statuses");
        if (serviceStatuses.containsKey(service.url)) {
            service.status = serviceStatuses.get(service.url);
        } else {
            service.status = "UNKNOWN";
            serviceStatuses.put(service.url, service.status);
        }
        return service;
    }

    public Future<RowSet<Row>> insertService(Service service) {
        ServiceEntity entity = new ServiceEntity();
        entity.name = service.name;
        entity.url = service.url;
        entity.createdAt = service.createdAt;
        return database.insertService(entity);
    }
}
