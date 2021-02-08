package com.github.adetiamarhadi.blog.client;

import com.proto.blog.Blog;
import com.proto.blog.BlogServiceGrpc;
import com.proto.blog.CreateBlogRequest;
import com.proto.blog.CreateBlogResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class BlogClient {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        createBlog(channel);

        System.out.println("Shutting down channel");
        channel.shutdown();
    }

    private static void createBlog(ManagedChannel channel) {

        BlogServiceGrpc.BlogServiceBlockingStub stub = BlogServiceGrpc.newBlockingStub(channel);

        Blog blog = Blog.newBuilder()
                .setAuthorId("Adet")
                .setTitle("gRPC Tutorial")
                .setContent("support for many languages")
                .build();

        CreateBlogRequest request = CreateBlogRequest.newBuilder()
                .setBlog(blog)
                .build();

        System.out.println("Send request for create blog");
        CreateBlogResponse response = stub.createBlog(request);
        System.out.println("response:");
        System.out.println(response.toString());
    }
}
