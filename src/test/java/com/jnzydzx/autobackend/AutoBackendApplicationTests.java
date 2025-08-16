package com.jnzydzx.autobackend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

@SpringBootTest
class AutoBackendApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(LocalTime.now());
    }

}
