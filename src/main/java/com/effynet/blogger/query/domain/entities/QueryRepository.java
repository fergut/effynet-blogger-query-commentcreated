package com.effynet.blogger.query.domain.entities;

import java.util.Optional;

public interface QueryRepository {
    void createComment(Comment comment);
    Optional<Comment> getComment(Comment comment);
}
