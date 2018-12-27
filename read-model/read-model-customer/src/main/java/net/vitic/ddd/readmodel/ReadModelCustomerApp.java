package net.vitic.ddd.readmodel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ReadModelCustomerApp {
    public static void main(String[] args) {
        SpringApplication.run(ReadModelCustomerApp.class, args);
    }
}
