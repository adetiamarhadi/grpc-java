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
}
