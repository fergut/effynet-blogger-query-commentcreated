package com.effynet.blogger.query.domain.entities;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DynamoDBQueryRepository implements QueryRepository{

    private DynamoDbClient dynamoDbClient;

    public DynamoDBQueryRepository (DynamoDbClient dynamoDbClient) {
        this.dynamoDbClient = dynamoDbClient;
    }

    @Override
    public void createComment(Comment comment) {

        HashMap<String, AttributeValue> itemValues = new HashMap<>();
        itemValues.put("PK", AttributeValue.builder().s("COMMENTPOSTID#" + comment.getPostId()).build());
        itemValues.put("SK", AttributeValue.builder().s("COMMENTID#" + comment.getId()).build());
        itemValues.put("id", AttributeValue.builder().s(comment.getId()).build());
        itemValues.put("postId", AttributeValue.builder().s(comment.getPostId()).build());
        itemValues.put("content", AttributeValue.builder().s(comment.getContent()).build());
        itemValues.put("status", AttributeValue.builder().s(comment.getStatus().toString()).build());

        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName("effynet-blogger-query")
                .item(itemValues)
                .build();

        PutItemResponse response = dynamoDbClient.putItem(putItemRequest);
    }


    @Override
    public Optional<Comment> getComment(Comment comment) {

        HashMap<String, AttributeValue> keyToGet = new HashMap<String, AttributeValue>();
        keyToGet.put("PK", AttributeValue.builder()
                .s("COMMENTPOSTID#" + comment.getPostId()).build());
        keyToGet.put("SK", AttributeValue.builder()
                .s("COMMENTID#" + comment.getId()).build());

        GetItemRequest getItemRequest = GetItemRequest.builder()
                .key(keyToGet)
                .tableName("effynet-blogger-query")
                .build();

        try {
            Map<String, AttributeValue> returnedItem = dynamoDbClient.getItem(getItemRequest).item();

            System.out.println("Returned item" + returnedItem);

            if (!returnedItem.isEmpty()) {
                Comment commentReturned = new Comment();
                commentReturned.setId(returnedItem.get("id").s());
                commentReturned.setPostId(returnedItem.get("postId").s());
                commentReturned.setContent(returnedItem.get("content").s());

                if (returnedItem.containsKey("status")) {
                    commentReturned.setStatus(Enum.valueOf(CommentStatus.class, returnedItem.get("status").s()));
                }

                return Optional.of(commentReturned);
            }
            else {
                return Optional.empty();
            }

        } catch (DynamoDbException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

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
