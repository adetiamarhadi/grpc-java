// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: calculator/calculator.proto

package com.proto.calculator;

public final class Calculator {
  private Calculator() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_calculator_CalculatorRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_calculator_CalculatorRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_calculator_CalculatorResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_calculator_CalculatorResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_calculator_NumberRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_calculator_NumberRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_calculator_NumberResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_calculator_NumberResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_calculator_AverageResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_calculator_AverageResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\033calculator/calculator.proto\022\ncalculato" +
      "r\")\n\021CalculatorRequest\022\t\n\001a\030\001 \001(\003\022\t\n\001b\030\002" +
      " \001(\003\"$\n\022CalculatorResponse\022\016\n\006result\030\001 \001" +
      "(\003\"\037\n\rNumberRequest\022\016\n\006number\030\001 \001(\003\" \n\016N" +
      "umberResponse\022\016\n\006result\030\001 \001(\003\"!\n\017Average" +
      "Response\022\016\n\006result\030\001 \001(\0012\354\001\n\021CalculatorS" +
      "ervice\022F\n\003sum\022\035.calculator.CalculatorReq" +
      "uest\032\036.calculator.CalculatorResponse\"\000\022H" +
      "\n\013PrimeNumber\022\031.calculator.NumberRequest" +
      "\032\032.calculator.NumberResponse\"\0000\001\022E\n\007aver" +
      "age\022\031.calculator.NumberRequest\032\033.calcula" +
      "tor.AverageResponse\"\000(\001B\030\n\024com.proto.cal" +
      "culatorP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_calculator_CalculatorRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_calculator_CalculatorRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_calculator_CalculatorRequest_descriptor,
        new java.lang.String[] { "A", "B", });
    internal_static_calculator_CalculatorResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_calculator_CalculatorResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_calculator_CalculatorResponse_descriptor,
        new java.lang.String[] { "Result", });
    internal_static_calculator_NumberRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_calculator_NumberRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_calculator_NumberRequest_descriptor,
        new java.lang.String[] { "Number", });
    internal_static_calculator_NumberResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_calculator_NumberResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_calculator_NumberResponse_descriptor,
        new java.lang.String[] { "Result", });
    internal_static_calculator_AverageResponse_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_calculator_AverageResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_calculator_AverageResponse_descriptor,
        new java.lang.String[] { "Result", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
