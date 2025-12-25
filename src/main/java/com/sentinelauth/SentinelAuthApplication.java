package com.sentinelauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * SentinelAuth - Spring Boot Application
 * 
 * Main entry point for the SentinelAuth application.
 * This class bootstraps the Spring Boot application with all necessary configurations.
 */
@SpringBootApplication
public class SentinelAuthApplication {

    /**
     * Main method to start the Spring Boot application
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(SentinelAuthApplication.class, args);
    }
}
