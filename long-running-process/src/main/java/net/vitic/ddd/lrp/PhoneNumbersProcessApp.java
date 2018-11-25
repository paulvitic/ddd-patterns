package net.vitic.ddd.lrp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PhoneNumbersProcessApp {
    public static void main(String[] args) {
        SpringApplication.run(PhoneNumbersProcessApp.class, args);
    }
}
