package com.effynet.blogger.query.domain.entities;

import java.util.Optional;

public class FakeQueryRepository implements QueryRepository {
    @Override
    public void createComment(Comment comment) {
    }

    @Override
    public Optional<Comment> getComment(Comment comment) {
        return Optional.of(comment);
    }
}
