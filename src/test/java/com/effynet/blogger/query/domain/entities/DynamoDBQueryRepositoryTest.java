package com.effynet.blogger.query.domain.entities;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

import java.net.URI;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DynamoDBQueryRepositoryTest {

    @Test
    @Disabled
    void createComment() {
        DynamoDbClient dynamoDbClient = getDynamoDbClient();
        DynamoDBQueryRepository dynamoDBQueryRepository = new DynamoDBQueryRepository(dynamoDbClient);

        String id = java.util.UUID.randomUUID().toString();
        String postId = java.util.UUID.randomUUID().toString();

        Comment comment = new Comment();
        comment.setId(id);
        comment.setPostId(postId);
        comment.setContent(id + " " + postId);

        assertDoesNotThrow(() -> dynamoDBQueryRepository.createComment(comment));
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

    @Test
    void getCommentDoesNotExists() {
        DynamoDbClient dynamoDbClient = getDynamoDbClient();
        QueryRepository dynamoDBQueryRepository = new DynamoDBQueryRepository(dynamoDbClient);

        Comment commentFilter = new Comment();
        commentFilter.setId("doesnotexist");
        commentFilter.setPostId("doesnotexist");

        Optional<Comment> comment = dynamoDBQueryRepository.getComment(commentFilter);

        assertEquals(true, comment.isEmpty());
    }
}