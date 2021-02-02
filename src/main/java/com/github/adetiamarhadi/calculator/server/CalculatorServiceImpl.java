package com.github.adetiamarhadi.calculator.server;

import com.proto.calculator.CalculatorRequest;
import com.proto.calculator.CalculatorResponse;
import com.proto.calculator.CalculatorServiceGrpc;
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
}
