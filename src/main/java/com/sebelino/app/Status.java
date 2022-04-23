package com.sebelino.app;

import java.util.List;

public class Status {
    public List<Service> services;

    public static Status of(List<Service> services) {
        Status obj = new Status();
        obj.services = services;
        return obj;
    }
}
