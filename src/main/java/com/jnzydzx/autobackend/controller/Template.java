package com.jnzydzx.autobackend.controller;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Template {
    private long startupTime;

    private record HealthResponse (
            String status,
            long time /* time / (1000 * 60 * 60) */
    ) {}

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady(ApplicationReadyEvent event) {
        this.startupTime = System.currentTimeMillis();
    }

    @GetMapping("/check")
    public HealthResponse check() {
        return new HealthResponse("200", System.currentTimeMillis() - startupTime);
    }
}
