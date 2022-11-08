package com.effynet.blogger.query.domain.entities;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import java.util.HashMap;

public class DynamoDBQueryRepository implements QueryRepository{
    @Override
    public void createComment(Comment comment) {
        DynamoDbClient dynamoDbClient = DynamoDbClient.builder()
                .region(Region.US_EAST_1)
                .build();

        HashMap<String, AttributeValue> itemValues = new HashMap<>();
        itemValues.put("PK", AttributeValue.builder().s("COMMENTPOSTID#" + comment.getPostId()).build());
        itemValues.put("SK", AttributeValue.builder().s("COMMENTID#" + comment.getId()).build());
        itemValues.put("id", AttributeValue.builder().s(comment.getId()).build());
        itemValues.put("postId", AttributeValue.builder().s(comment.getPostId()).build());
        itemValues.put("content", AttributeValue.builder().s(comment.getContent()).build());

        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName("effynet-blogger-query")
                .item(itemValues)
                .build();

        PutItemResponse response = dynamoDbClient.putItem(putItemRequest);
    }
}
