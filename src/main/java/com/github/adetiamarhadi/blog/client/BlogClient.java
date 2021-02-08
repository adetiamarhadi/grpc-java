package com.github.adetiamarhadi.blog.client;

import com.proto.blog.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

public class BlogClient {

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

//        String blogId = createBlog(channel);

//        readBlog(channel, blogId);
//        readBlog(channel, "6020f649c796f97f5a83b4d1");
//        readBlog(channel, "A1381XMHA");
        
//        updateBlog(channel, blogId);
        deleteBlog(channel, "6020dd43580404578320e4fe");
        readBlog(channel, "6020dd43580404578320e4fe");
        deleteBlog(channel, "6020dd43580404578320e4fe");
        deleteBlog(channel, "A1381XMHA");

        System.out.println("Shutting down channel");
        channel.shutdown();
    }

    private static void deleteBlog(ManagedChannel channel, String blogId) {

        BlogServiceGrpc.BlogServiceBlockingStub stub = BlogServiceGrpc.newBlockingStub(channel);

        DeleteBlogRequest request = DeleteBlogRequest.newBuilder()
                .setBlogId(blogId)
                .build();

        System.out.println("Sending request for delete blog");
        try {
            DeleteBlogResponse response = stub.deleteBlog(request);
            System.out.println("deleted blog: " + response.getBlogId());
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode().equals(Status.Code.NOT_FOUND)) {
                System.out.println("result search for blog id "+ blogId +": not found");
            } else {
                e.printStackTrace();
            }
        }
    }

    private static void updateBlog(ManagedChannel channel, String blogId) {

        BlogServiceGrpc.BlogServiceBlockingStub stub = BlogServiceGrpc.newBlockingStub(channel);

        Blog blog = Blog.newBuilder()
                .setId(blogId)
                .setAuthorId("Adetia Marhadi")
                .setTitle("new gRPC Tutorial v2021")
                .setContent("support for many languages v2021")
                .build();

        System.out.println("Sending request for update blog");
        try {

            UpdateBlogRequest request = UpdateBlogRequest.newBuilder()
                    .setBlog(blog)
                    .build();

            UpdateBlogResponse response = stub.updateBlog(request);
            System.out.println("result after updated");
            System.out.println(response.toString());
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode().equals(Status.Code.NOT_FOUND)) {
                System.out.println("result search for blog id "+ blogId +": not found");
            } else {
                e.printStackTrace();
            }
        }
    }

    private static void readBlog(ManagedChannel channel, String blogId) {

        BlogServiceGrpc.BlogServiceBlockingStub stub = BlogServiceGrpc.newBlockingStub(channel);

        System.out.println("Send a request search for blog id: " + blogId);
        try {
            ReadBlogResponse response = stub.readBlog(ReadBlogRequest.newBuilder()
                    .setBlogId(blogId)
                    .build());

            System.out.println("result search for blog id "+ blogId);
            System.out.println(response.toString());
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode().equals(Status.Code.NOT_FOUND)) {
                System.out.println("result search for blog id "+ blogId +": not found");
            } else {
                e.printStackTrace();
            }
        }
    }

    private static String createBlog(ManagedChannel channel) {

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

        return response.getBlog().getId();
    }
}
