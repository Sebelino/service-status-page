package com.sebelino.app.repository;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sebelino.app.Service;

import java.time.LocalDateTime;

class ServiceEntity {
    public String name;
    public String url;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS")
    public LocalDateTime createdAt;

    Service toModel() {
        Service service = new Service();
        service.name = name;
        service.url = url;
        service.createdAt = createdAt;
        return service;
    }
}
