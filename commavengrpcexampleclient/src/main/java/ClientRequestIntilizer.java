

import com.maven.grpc.example.CommunicateServiceGrpc;
import com.maven.grpc.example.GrpcService;
import io.grpc.*;

public class ClientRequestIntilizer {
//creating a request for fetching details of Employee with id 1
    public static void main(String[] args) {
        GrpcService.ClientRequest clientRequest = GrpcService.ClientRequest.newBuilder().setEmployeeName("Shivankur").build();
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        CommunicateServiceGrpc.CommunicateServiceBlockingStub stub = CommunicateServiceGrpc.newBlockingStub(channel);
        GrpcService.ClientResponse clientResponse = stub.fetchEmployee(clientRequest);
        System.out.print("This is your Server Responce \n"+clientResponse.getEmployee());
        channel.shutdown();
    }
}