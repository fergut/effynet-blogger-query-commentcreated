package com.effynet.blogger.query.domain.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentServiceTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createCommentTest() {
        FakeQueryRepository fakeQueryRepository = new FakeQueryRepository();
        CommentService commentService = new CommentService(fakeQueryRepository);

        Comment comment = new Comment();
        comment.setId("1");
        comment.setPostId("100");
        comment.setContent("My first comment");

        assertDoesNotThrow(() -> commentService.create(comment));
    }

}