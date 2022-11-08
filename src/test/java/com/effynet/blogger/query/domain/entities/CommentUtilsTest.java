package com.effynet.blogger.query.domain.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentUtilsTest {

    @Test
    void getCommentFromJsonString() {
        String commentJsonString = "{\"id\":\"1\", \"postId\":\"10\",\"content\":\"my first comment\"}";

        Comment comment = CommentUtils.getCommentFromJsonString(commentJsonString);

        assertEquals("1", comment.getId());
        assertEquals("10", comment.getPostId());
        assertEquals("my first comment", comment.getContent());
    }
}