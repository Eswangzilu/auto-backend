package com.jnzydzx.autobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AutoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(AutoBackendApplication.class, args);
    }

}
