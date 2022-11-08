package com.effynet.blogger.query;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSBatchResponse;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.effynet.blogger.query.domain.entities.CommentService;
import com.effynet.blogger.query.domain.entities.CommentUtils;
import com.effynet.blogger.query.domain.entities.DynamoDBQueryRepository;
import com.effynet.blogger.query.domain.entities.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentCreatedHandler implements RequestHandler<SQSEvent, SQSBatchResponse> {

    @Override
    public SQSBatchResponse handleRequest(SQSEvent input, Context context) {

        DynamoDBQueryRepository dynamoDBQueryRepository = new DynamoDBQueryRepository();
        CommentService commentService = new CommentService(dynamoDBQueryRepository);

        List<SQSBatchResponse.BatchItemFailure> batchItemFailure = new ArrayList<SQSBatchResponse.BatchItemFailure>();
        String messageId = "";

        for (SQSEvent.SQSMessage message: input.getRecords()) {
            try {
                messageId = message.getMessageId();
                System.out.println(messageId);

                String commentJsonString = message.getBody();
                System.out.println(commentJsonString);

                Comment comment = CommentUtils.getCommentFromJsonString(commentJsonString);
                System.out.println(comment);

                commentService.create(comment);
                System.out.println("OK");
            } catch (Exception e) {
                System.out.println(messageId);
                batchItemFailure.add(new SQSBatchResponse.BatchItemFailure(messageId));
            }
        }

        return new SQSBatchResponse(batchItemFailure);
    }

}
