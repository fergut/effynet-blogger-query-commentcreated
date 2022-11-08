package com.effynet.blogger.query.domain.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CommentUtils {

    public static Comment getCommentFromJsonString(String commentJsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(commentJsonString, Comment.class);
    }
}
