package com.github.adetiamarhadi.greeting.client;

import com.proto.calculator.CalculatorRequest;
import com.proto.calculator.CalculatorResponse;
import com.proto.calculator.CalculatorServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClient {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        CalculatorServiceGrpc.CalculatorServiceBlockingStub client = CalculatorServiceGrpc.newBlockingStub(channel);

        CalculatorRequest calculatorRequest = CalculatorRequest.newBuilder()
                .setA(10)
                .setB(20)
                .build();

        CalculatorResponse response = client.sum(calculatorRequest);
        System.out.println("result: " + response.getResult());

        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}
