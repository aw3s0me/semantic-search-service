package de.bonn.eis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by korovin on 3/18/2017.
 * Run in for testing purposes
 * TODO: create docker and war for real server deployment
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
