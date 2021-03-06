package com.sebelino.app;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Service {
    public String name;
    public String url;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSS")
    public LocalDateTime createdAt;
    public String status;
}
