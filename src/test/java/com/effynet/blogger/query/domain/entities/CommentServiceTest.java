package com.effynet.blogger.query.domain.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class CommentServiceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createComment() {
        FakeQueryRepository fakeQueryRepository = new FakeQueryRepository();
        CommentService commentService = new CommentService(fakeQueryRepository);

        String id = java.util.UUID.randomUUID().toString();
        String postId = java.util.UUID.randomUUID().toString();

        Comment comment = new Comment();
        comment.setId(id);
        comment.setPostId(postId);
        comment.setContent(id + " " + postId);

        assertDoesNotThrow(() -> commentService.create(comment));
    }

    @Test
    void createCommentIntegrationTest() {
        DynamoDbClient dynamoDbClient = getDynamoDbClient();
        DynamoDBQueryRepository dynamoDBQueryRepository = new DynamoDBQueryRepository(dynamoDbClient);
        CommentService commentService = new CommentService(dynamoDBQueryRepository);

        String id = java.util.UUID.randomUUID().toString();
        String postId = java.util.UUID.randomUUID().toString();

        Comment comment = new Comment();
        comment.setId(id);
        comment.setPostId(postId);
        comment.setContent(id + " " + postId);

        assertDoesNotThrow(() -> commentService.create(comment));
    }

    private DynamoDbClient getDynamoDbClient(String endpoint) {
        DynamoDbClientBuilder builder = DynamoDbClient.builder();

        builder.region(Region.US_EAST_1);

        if (endpoint != null && !endpoint.isEmpty()) {
            builder.endpointOverride(URI.create(endpoint));
        }

        return builder.build();
    }

    private DynamoDbClient getDynamoDbClient() {
        return getDynamoDbClient("");
    }

}