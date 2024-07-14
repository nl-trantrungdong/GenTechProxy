package com.gentech.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GenTechProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenTechProxyApplication.class, args);
    }

}
