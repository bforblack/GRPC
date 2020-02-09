package com.maven.grpc.example.grpcServiceImpl;

import com.maven.grpc.example.EmpService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class MainClass {
   //Creating a Server
    public static void main(String[] args) throws Exception{
        System.out.println("Startin Server");
        Server server = ServerBuilder.forPort(8080).addService(new EmpService()).build();
        server.start();
        System.out.println("Server Started");
        server.awaitTermination();
        System.out.println("Server Ended");
    }
}