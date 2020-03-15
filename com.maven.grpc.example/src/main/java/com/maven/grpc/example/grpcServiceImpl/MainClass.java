package com.maven.grpc.example.grpcServiceImpl;

import com.maven.grpc.example.proto.service.implementation.EmpService;
import com.maven.grpc.example.proto.service.implementation.EmpStreamServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;

public class MainClass {
   //Creating a Server
    public static void main(String[] args) throws Exception{
        System.out.println("Startin Server");
      //  Server server = ServerBuilder.forPort(8080).addService(new EmpService()).build();
        Server server = ServerBuilder.forPort(8080).addService(new EmpStreamServiceImpl()).build();
        server.start();
        System.out.println("Server Started");
        server.awaitTermination();
        System.out.println("Server Ended");
    }
}