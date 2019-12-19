package com.example.grpc_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class GrpcApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(GrpcApiApplication.class, args);

        try {
            ServerGrpc.run();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
