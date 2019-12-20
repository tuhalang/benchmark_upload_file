package com.example.grpc_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class GrpcApiApplication {

    public static void main(String[] args) throws IOException, InterruptedException {

        ConfigurableApplicationContext context = SpringApplication.run(GrpcApiApplication.class, args);

        context.getBean(ServerGrpc.class).run();

    }

}
