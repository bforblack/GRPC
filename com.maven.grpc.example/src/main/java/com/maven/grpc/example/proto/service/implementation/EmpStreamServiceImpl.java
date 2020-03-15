package com.maven.grpc.example.proto.service.implementation;

import com.google.gson.Gson;
import com.maven.grpc.example.CommunicateServiceGrpc;
import com.maven.grpc.example.CommunicateStreamServiceGrpc;
import com.maven.grpc.example.GrpcStream;
import com.maven.grpc.example.server.entity.HibernateUtils;
import io.grpc.stub.StreamObserver;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import javax.persistence.Query;

public class EmpStreamServiceImpl extends CommunicateStreamServiceGrpc.CommunicateStreamServiceImplBase {
    private Gson gson = new Gson();
    @Override
    public StreamObserver<GrpcStream.StreamDataRequest> communicateStreamServiceDynamic(
            final StreamObserver<com.maven.grpc.example.GrpcStream.ClientStreamResponse> responseObserver) {
        StreamObserver<com.maven.grpc.example.GrpcStream.StreamDataRequest> streamObserver = new StreamObserver<com.maven.grpc.example.GrpcStream.StreamDataRequest>(){
            @Override
            public void onNext(com.maven.grpc.example.GrpcStream.StreamDataRequest valueStreamDataRequest) {
                System.out.println("OnNext");
                String query =  valueStreamDataRequest.getQuery();
                responseObserver.onNext(executeQuery(query));
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("ERROR");
            }

            @Override
            public void onCompleted() {
                System.out.println("Oncompleted Called");
            }
        };
        return streamObserver;
    }
    private com.maven.grpc.example.GrpcStream.ClientStreamResponse executeQuery(final String query) {
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Query q =session.createNativeQuery(query);
        com.maven.grpc.example.GrpcStream.ClientStreamResponse response = com.maven.grpc.example.GrpcStream.ClientStreamResponse.newBuilder().setStreamDataResponse(gson.toJson(q.getResultList())).build();
        session.close();
        System.out.print("======Session Closed===");
        return response;
    }
}