package com.gentech.proxy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
public class Proxy {
    private String ip;
    private int port;
    private boolean isLive;
    private String country;

    // Constructors, getters, and setters
}
