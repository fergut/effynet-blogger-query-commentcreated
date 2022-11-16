package com.effynet.blogger.query;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSBatchResponse;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.effynet.blogger.query.domain.entities.CommentService;
import com.effynet.blogger.query.domain.entities.DynamoDBQueryRepository;
import com.effynet.blogger.query.domain.entities.Comment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClientBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class CommentCreatedHandler implements RequestHandler<SQSEvent, SQSBatchResponse> {

    @Override
    public SQSBatchResponse handleRequest(SQSEvent input, Context context) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        DynamoDbClient dynamoDbClient = getDynamoDbClient();
        DynamoDBQueryRepository dynamoDBQueryRepository = new DynamoDBQueryRepository(dynamoDbClient);
        CommentService commentService = new CommentService(dynamoDBQueryRepository);

        List<SQSBatchResponse.BatchItemFailure> batchItemFailure = new ArrayList<SQSBatchResponse.BatchItemFailure>();
        String messageId = "";

        for (SQSEvent.SQSMessage message: input.getRecords()) {
            try {
                messageId = message.getMessageId();
                System.out.println(messageId);

                String commentJsonString = message.getBody();
                System.out.println(commentJsonString);

                Comment comment = gson.fromJson(commentJsonString, Comment.class);
                System.out.println(comment);

                commentService.create(comment);
                System.out.println("OK");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println(messageId);
                batchItemFailure.add(new SQSBatchResponse.BatchItemFailure(messageId));
            }
        }

        return new SQSBatchResponse(batchItemFailure);
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
