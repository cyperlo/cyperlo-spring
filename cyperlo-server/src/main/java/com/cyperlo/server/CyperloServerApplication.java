package com.cyperlo.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenhailong
 */
@SpringBootApplication(scanBasePackages = {"com.cyperlo"})
public class CyperloServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CyperloServerApplication.class, args);
    }

}
