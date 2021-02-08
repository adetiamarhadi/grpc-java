package com.github.adetiamarhadi.blog.server;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.proto.blog.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.bson.Document;
import org.bson.types.ObjectId;

public class BlogServiceImpl extends BlogServiceGrpc.BlogServiceImplBase {

    private MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    private MongoDatabase mongoDatabase = this.mongoClient.getDatabase("mydb");
    private MongoCollection<Document> mongoCollection = this.mongoDatabase.getCollection("blog");

    @Override
    public void createBlog(CreateBlogRequest request, StreamObserver<CreateBlogResponse> responseObserver) {

        System.out.println("Received create blog request");

        Blog blog = request.getBlog();

        Document document = new Document("author_id", blog.getAuthorId())
                .append("title", blog.getTitle())
                .append("content", blog.getContent());

        this.mongoCollection.insertOne(document);

        String id = document.getObjectId("_id").toString();
        System.out.println("Blog created with id: " + id);

        CreateBlogResponse blogResponse = CreateBlogResponse.newBuilder()
                .setBlog(Blog.newBuilder()
                        .setAuthorId(blog.getAuthorId())
                        .setContent(blog.getContent())
                        .setTitle(blog.getTitle())
                        .setId(id)
                        .build())
                .build();

        responseObserver.onNext(blogResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void readBlog(ReadBlogRequest request, StreamObserver<ReadBlogResponse> responseObserver) {

        System.out.println("Received read blog request");

        String blogId = request.getBlogId();

        System.out.println("Find blog with id: " + blogId);

        Document document = null;
        try {
            document = this.mongoCollection.find(Filters.eq("_id", new ObjectId(blogId))).first();
        } catch (Exception e) {
            System.out.println("something error: " + e.getMessage());
        }

        if (null == document) {

            System.out.println("Blog with id "+ blogId +" not found");
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("the Blog ID was not found")
                    .asRuntimeException());
        } else {

            System.out.println("Founded blog with id " + blogId);
            Blog blog = Blog.newBuilder()
                    .setAuthorId(document.getString("author_id"))
                    .setContent(document.getString("content"))
                    .setTitle(document.getString("title"))
                    .setId(blogId)
                    .build();

            System.out.println("Sending response");
            responseObserver.onNext(ReadBlogResponse.newBuilder()
                    .setBlog(blog)
                    .build());
            responseObserver.onCompleted();
        }
    }
}
