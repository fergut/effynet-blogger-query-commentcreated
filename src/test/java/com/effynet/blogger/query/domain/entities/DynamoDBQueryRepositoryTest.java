package com.effynet.blogger.query.domain.entities;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamoDBQueryRepositoryTest {

    @Test
    @Disabled
    void createComment() {
        DynamoDBQueryRepository dynamoDBQueryRepository = new DynamoDBQueryRepository();
        Comment comment = new Comment();
        comment.setId("10");
        comment.setPostId("100");
        comment.setContent("My first commment");

        assertDoesNotThrow(() -> dynamoDBQueryRepository.createComment(comment));
    }
}