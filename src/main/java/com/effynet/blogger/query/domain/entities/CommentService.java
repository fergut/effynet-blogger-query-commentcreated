package com.effynet.blogger.query.domain.entities;

import java.util.Optional;

public class CommentService {

    private QueryRepository queryRepository;

    public CommentService(QueryRepository queryRepository) {
       this.queryRepository = queryRepository;
    }

    public void create(Comment comment) {

        comment.setStatus(CommentStatus.InReview);

        Optional<Comment> commentDB = this.queryRepository.getComment(comment);

        if (commentDB.isEmpty()) {
            this.queryRepository.createComment(comment);
        }

    }

}
