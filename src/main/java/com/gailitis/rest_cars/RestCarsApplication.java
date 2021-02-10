package com.gailitis.rest_cars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.io.FileNotFoundException;

@SpringBootApplication
public class RestCarsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestCarsApplication.class, args);
    }



}
