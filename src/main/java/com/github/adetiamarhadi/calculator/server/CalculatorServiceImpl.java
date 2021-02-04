package com.github.adetiamarhadi.calculator.server;

import com.proto.calculator.*;
import io.grpc.stub.StreamObserver;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {

    @Override
    public void sum(CalculatorRequest request, StreamObserver<CalculatorResponse> responseObserver) {
        long a = request.getA();
        long b = request.getB();

        long result = a + b;

        CalculatorResponse calculatorResponse = CalculatorResponse.newBuilder()
                .setResult(result)
                .build();

        responseObserver.onNext(calculatorResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void primeNumber(NumberRequest request, StreamObserver<NumberResponse> responseObserver) {

        long number = request.getNumber();

        long k = 2;

        try {
            while (number > 1) {

                if (number % k == 0) {
                    responseObserver.onNext(NumberResponse.newBuilder()
                            .setResult(k)
                            .build());
                    number = number / k;
                } else {
                    k = k + 1;
                }

                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            responseObserver.onCompleted();
        }
    }

    @Override
    public StreamObserver<NumberRequest> average(StreamObserver<AverageResponse> responseObserver) {

        StreamObserver<NumberRequest> requestStreamObserver = new StreamObserver<>() {

            long count = 0;
            int total = 0;

            @Override
            public void onNext(NumberRequest value) {
                total += value.getNumber();
                count++;
            }

            @Override
            public void onError(Throwable t) {
                // IGNORE
            }

            @Override
            public void onCompleted() {
                double result = (double) total/count;
                responseObserver.onNext(AverageResponse.newBuilder()
                        .setResult(result)
                        .build());
                responseObserver.onCompleted();
            }
        };

        return requestStreamObserver;
    }

    @Override
    public StreamObserver<NumberRequest> findMaximum(StreamObserver<NumberResponse> responseObserver) {

        StreamObserver<NumberRequest> requestStreamObserver = new StreamObserver<>() {

            long max = Long.MIN_VALUE;

            @Override
            public void onNext(NumberRequest value) {
                if (value.getNumber() > max) {
                    max = value.getNumber();
                    responseObserver.onNext(NumberResponse.newBuilder()
                            .setResult(max)
                            .build());
                }
            }

            @Override
            public void onError(Throwable t) {
                // IGNORE
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };

        return requestStreamObserver;
    }
}
