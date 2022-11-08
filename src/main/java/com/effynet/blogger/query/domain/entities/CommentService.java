package com.effynet.blogger.query.domain.entities;

public class CommentService {

    private QueryRepository queryRepository;

    public CommentService(QueryRepository queryRepository) {
       this.queryRepository = queryRepository;
    }

    public void create(Comment comment) {
        this.queryRepository.createComment(comment);
    }

}
