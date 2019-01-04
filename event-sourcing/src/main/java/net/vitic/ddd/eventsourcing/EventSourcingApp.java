package net.vitic.ddd.eventsourcing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class EventSourcingApp {
    public static void main(String[] args) {
        SpringApplication.run(EventSourcingApp.class, args);
    }
}
