package com.github.adetiamarhadi.calculator.client;

import com.proto.calculator.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CalculatorClient {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

//        unary(channel);

//        serverStreaming(channel);

//        clientStreaming(channel);

//        serverClientStreaming(channel);

        returnError(channel);

        System.out.println("Shutting down channel");
        channel.shutdown();
    }

    private static void returnError(ManagedChannel channel) {

        CalculatorServiceGrpc.CalculatorServiceBlockingStub serviceBlockingStub = CalculatorServiceGrpc
                .newBlockingStub(channel);

        int number = -1;

        try {
            serviceBlockingStub.squareRoot(SquareRootRequest.newBuilder()
                    .setNumber(number)
                    .build());
        } catch (StatusRuntimeException e) {
            System.out.println("Got an Error!");
            e.printStackTrace();
        }
    }

    private static void serverClientStreaming(ManagedChannel channel) {

        CountDownLatch latch = new CountDownLatch(1);

        CalculatorServiceGrpc.CalculatorServiceStub client = CalculatorServiceGrpc.newStub(channel);

        StreamObserver<NumberRequest> requestStreamObserver = client.findMaximum(new StreamObserver<>() {
            @Override
            public void onNext(NumberResponse value) {
                System.out.println("max number now is: " + value.getResult());
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

        Arrays.asList(1,5,3,6,2,20).forEach(number -> requestStreamObserver.onNext(NumberRequest.newBuilder()
                .setNumber(number)
                .build()));

        requestStreamObserver.onCompleted();

        try {
            latch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void clientStreaming(ManagedChannel channel) {

        CountDownLatch latch = new CountDownLatch(1);

        CalculatorServiceGrpc.CalculatorServiceStub client = CalculatorServiceGrpc.newStub(channel);

        StreamObserver<NumberRequest> requestStreamObserver = client.average(new StreamObserver<>() {
            @Override
            public void onNext(AverageResponse value) {
                System.out.println("result: " + value.getResult());
            }

            @Override
            public void onError(Throwable t) {
                // IGNORE
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        });

        System.out.println("sending some request");
        requestStreamObserver.onNext(NumberRequest.newBuilder().setNumber(4).build());
        requestStreamObserver.onNext(NumberRequest.newBuilder().setNumber(8).build());
        requestStreamObserver.onNext(NumberRequest.newBuilder().setNumber(7).build());
        requestStreamObserver.onNext(NumberRequest.newBuilder().setNumber(12).build());
        requestStreamObserver.onNext(NumberRequest.newBuilder().setNumber(2).build());
        System.out.println("send request completed");

        requestStreamObserver.onCompleted();

        try {
            latch.await(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void serverStreaming(ManagedChannel channel) {

        CalculatorServiceGrpc.CalculatorServiceBlockingStub client = CalculatorServiceGrpc.newBlockingStub(channel);

        NumberRequest numberRequest = NumberRequest.newBuilder()
                .setNumber(120)
                .build();

        client.primeNumber(numberRequest).forEachRemaining(numberResponse -> {
            System.out.println("result: " + numberResponse.getResult());
        });
    }

    private static void unary(ManagedChannel channel) {

        CalculatorServiceGrpc.CalculatorServiceBlockingStub client = CalculatorServiceGrpc.newBlockingStub(channel);

        CalculatorRequest calculatorRequest = CalculatorRequest.newBuilder()
                .setA(10)
                .setB(20)
                .build();

        CalculatorResponse response = client.sum(calculatorRequest);
        System.out.println("result: " + response.getResult());
    }
}
