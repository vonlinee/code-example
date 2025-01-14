package org.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;

/**
 *
 */
@SpringBootApplication
@ServletComponentScan
@ImportResource(value = {"classpath:obs.properties"})
public class MainApplication {
    public static void main(String[] args) {
        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication application = new SpringApplication(MainApplication.class);
        ConfigurableApplicationContext context = SpringApplication.run(MainApplication.class, args);
    }
}
