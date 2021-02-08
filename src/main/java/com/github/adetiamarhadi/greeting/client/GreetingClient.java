package com.github.adetiamarhadi.greeting.client;

import com.proto.dummy.DummyServiceGrpc;
import com.proto.greet.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.netty.shaded.io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.StreamObserver;

import javax.net.ssl.SSLException;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GreetingClient {

    public static void main(String[] args) throws SSLException {

        System.out.println("Hello I'm gRPC client");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext() // for ALPN error
                .build();

        ManagedChannel secureChannel = NettyChannelBuilder.forAddress("localhost", 50051)
                .sslContext(GrpcSslContexts.forClient()
                        .trustManager(new File("ssl/ca.crt"))
                        .build())
                .build();

        System.out.println("Creating stub");

//        unary(channel);
        unary(secureChannel);

//        serverStreaming(channel);

//        clientStreaming(channel);

//        serverClientStreaming(channel);

//        returnDeadline(channel);

        System.out.println("Shutting down channel");
        channel.shutdown();
    }

    private static void returnDeadline(ManagedChannel channel) {

        GreetServiceGrpc.GreetServiceBlockingStub serviceBlockingStub = GreetServiceGrpc.newBlockingStub(channel);

        try {

            System.out.println("sending a request with a deadline of 500 ms");
            GreetWithDeadlineResponse response = serviceBlockingStub.withDeadlineAfter(3000, TimeUnit.MILLISECONDS).greetWithDeadline(
                    GreetWithDeadlineRequest.newBuilder()
                            .setGreeting(Greeting.newBuilder()
                                    .setFirstName("Adet")
                                    .build())
                            .build());
            System.out.println("result: " + response.getResult());
        } catch (StatusRuntimeException e) {
            if (e.getStatus() == Status.DEADLINE_EXCEEDED) {
                System.out.println("deadline has been exceeded, we don't want the response");
            } else {
                e.printStackTrace();
            }
        }

        try {

            System.out.println("sending a request with a deadline of 100 ms");
            GreetWithDeadlineResponse response = serviceBlockingStub.withDeadlineAfter(100, TimeUnit.MILLISECONDS).greetWithDeadline(
                    GreetWithDeadlineRequest.newBuilder()
                            .setGreeting(Greeting.newBuilder()
                                    .setFirstName("Adet")
                                    .build())
                            .build());
            System.out.println("result: " + response.getResult());
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode().equals(Status.DEADLINE_EXCEEDED.getCode())) {
                System.out.println("deadline has been exceeded, we don't want the response");
            }
            e.printStackTrace();
        }
    }

    private static void serverClientStreaming(ManagedChannel channel) {

        CountDownLatch latch = new CountDownLatch(1);

        GreetServiceGrpc.GreetServiceStub client = GreetServiceGrpc.newStub(channel);

        StreamObserver<GreetEveryoneRequest> requestStreamObserver = client.greetEveryone(new StreamObserver<>() {
            @Override
            public void onNext(GreetEveryoneResponse value) {
                System.out.println("result: " + value.getResult());
            }

            @Override
            public void onError(Throwable t) {
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        });

        Arrays.asList("Cahyo", "Cahyadi", "Cangmuni", "Cahgiri", "Cahmeni").forEach(name -> requestStreamObserver.onNext(
                GreetEveryoneRequest.newBuilder()
                        .setGreeting(Greeting.newBuilder()
                                .setFirstName(name)
                                .build())
                        .build()));

        requestStreamObserver.onCompleted();

        try {
            latch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void clientStreaming(ManagedChannel channel) {

        GreetServiceGrpc.GreetServiceStub client = GreetServiceGrpc.newStub(channel);

        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<LongGreetRequest> requestObserver = client.longGreet(new StreamObserver<>() {
            @Override
            public void onNext(LongGreetResponse value) {
                System.out.println("Received a response from the server");
                System.out.println(value.getResult());
            }

            @Override
            public void onError(Throwable t) {
                // IGNORE
            }

            @Override
            public void onCompleted() {
                System.out.println("Server has completed sending us something");
                latch.countDown();
            }
        });

        System.out.println("Send request #1");
        requestObserver.onNext(LongGreetRequest.newBuilder()
                .setGreeting(Greeting.newBuilder()
                        .setFirstName("Adetia")
                        .setLastName("Marhadi")
                        .build())
                .build());

        System.out.println("Send request #2");
        requestObserver.onNext(LongGreetRequest.newBuilder()
                .setGreeting(Greeting.newBuilder()
                        .setFirstName("Sumardono")
                        .setLastName("Ayano")
                        .build())
                .build());

        System.out.println("Send request #3");
        requestObserver.onNext(LongGreetRequest.newBuilder()
                .setGreeting(Greeting.newBuilder()
                        .setFirstName("Mejiku")
                        .setLastName("Hibiniyu")
                        .build())
                .build());

        System.out.println("Send request completed");
        requestObserver.onCompleted();

        try {
            latch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void serverStreaming(ManagedChannel channel) {

        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        GreetManyTimesRequest greetManyTimesRequest = GreetManyTimesRequest.newBuilder()
                .setGreeting(Greeting.newBuilder()
                        .setFirstName("Adetia")
                        .setLastName("Marhadi")
                        .build())
                .build();

        greetClient.greetManyTimes(greetManyTimesRequest).forEachRemaining(greetManyTimesResponse -> {
            System.out.println(greetManyTimesResponse.getResult());
        });
    }

    private static void unary(ManagedChannel channel) {

        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        Greeting greeting = Greeting.newBuilder()
                .setFirstName("Adetia")
                .setLastName("Marhadi")
                .build();

        GreetRequest greetRequest = GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build();

        GreetResponse greetResponse = greetClient.greet(greetRequest);
        System.out.println("result: " + greetResponse.getResult());
    }
}
