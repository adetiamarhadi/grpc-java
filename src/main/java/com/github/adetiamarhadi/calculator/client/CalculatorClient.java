package com.github.adetiamarhadi.calculator.client;

import com.proto.calculator.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

public class CalculatorClient {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        CalculatorServiceGrpc.CalculatorServiceBlockingStub client = CalculatorServiceGrpc.newBlockingStub(channel);

//        unary(client);

        serverStreaming(client);

        System.out.println("Shutting down channel");
        channel.shutdown();
    }

    private static void serverStreaming(CalculatorServiceGrpc.CalculatorServiceBlockingStub client) {
        NumberRequest numberRequest = NumberRequest.newBuilder()
                .setNumber(120)
                .build();

        client.primeNumber(numberRequest).forEachRemaining(numberResponse -> {
            System.out.println("result: " + numberResponse.getResult());
        });
    }

    private static void unary(CalculatorServiceGrpc.CalculatorServiceBlockingStub client) {
        CalculatorRequest calculatorRequest = CalculatorRequest.newBuilder()
                .setA(10)
                .setB(20)
                .build();

        CalculatorResponse response = client.sum(calculatorRequest);
        System.out.println("result: " + response.getResult());
    }
}
