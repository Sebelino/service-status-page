package com.sebelino.app;

import java.util.List;

public class ServiceSet {
    public List<Service> services;

    public static ServiceSet of(List<Service> services) {
        ServiceSet obj = new ServiceSet();
        obj.services = services;
        return obj;
    }
}
