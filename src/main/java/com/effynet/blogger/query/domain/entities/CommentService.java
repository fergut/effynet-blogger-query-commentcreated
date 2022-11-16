package com.effynet.blogger.query.domain.entities;

import java.util.Optional;

public class CommentService {

    private QueryRepository queryRepository;

    public CommentService(QueryRepository queryRepository) {
       this.queryRepository = queryRepository;
    }

    public void create(Comment comment) {

        comment.setStatus(CommentStatus.InReview);

        System.out.println("Comment : " + comment);

        Optional<Comment> commentDB = this.queryRepository.getComment(comment);

        System.out.println("CommentDB empty: " + commentDB.isEmpty());

        if (commentDB.isEmpty()) {
            this.queryRepository.createComment(comment);
        }

    }

}
