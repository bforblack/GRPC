import com.maven.grpc.example.CommunicateStreamServiceGrpc;
import com.maven.grpc.example.GrpcStream;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ClientStreamRequestInitilizer {

    final static CountDownLatch finishedLatch = new CountDownLatch(1);

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
        //for aysnc Stub use newStub  & for Sync stubs use future Stub
        CommunicateStreamServiceGrpc.CommunicateStreamServiceStub asyncStub = CommunicateStreamServiceGrpc.newStub(managedChannel);
        StreamObserver<GrpcStream.StreamDataRequest> requestObserver = asyncStub.communicateStreamServiceDynamic(new StreamObserver<GrpcStream.ClientStreamResponse>() {
            @Override
            public void onNext(GrpcStream.ClientStreamResponse clientStreamResponse) {
                System.out.println("This is your Serserv Responce \n" + clientStreamResponse.getStreamDataResponse());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Exception Caught While Sending Tream Data With Error==" + throwable.toString());
            }

            @Override
            public void onCompleted() {
                System.out.println("Execution Completed ");
            }
        });

        processQueryList().parallelStream().forEach(x -> {
            requestObserver.onNext(GrpcStream.StreamDataRequest.newBuilder().setQuery(x).build());

        });
        finishedLatch.await();
    }

    //returns a user defined query for processing from server side
    private static List<String> processQueryList() {
        List<String> queryList = new ArrayList<String>();
        queryList.add("select *from ChotaAmazon");
        queryList.add("select id1 from ChotaAmazon");
//queryList.add("select * from user");
        return queryList;
    }
}
