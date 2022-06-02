package by.innowise.internship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.FeignClient;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableReactiveFeignClients
@FeignClient
public class ReactiveRun {

    public static void main(String[] args) {

        SpringApplication.run(ReactiveRun.class, args);
    }
}
