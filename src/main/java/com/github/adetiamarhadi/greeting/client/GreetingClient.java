package com.github.adetiamarhadi.greeting.client;

import com.proto.dummy.DummyServiceGrpc;
import com.proto.greet.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {

    public static void main(String[] args) {

        System.out.println("Hello I'm gRPC client");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext() // for ALPN error
                .build();

        System.out.println("Creating stub");

        GreetServiceGrpc.GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

//        unary(greetClient);

        serverStreaming(greetClient);

        System.out.println("Shutting down channel");
        channel.shutdown();
    }

    private static void serverStreaming(GreetServiceGrpc.GreetServiceBlockingStub greetClient) {
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

    private static void unary(GreetServiceGrpc.GreetServiceBlockingStub greetClient) {
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
