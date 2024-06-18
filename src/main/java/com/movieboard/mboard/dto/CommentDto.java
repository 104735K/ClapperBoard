package com.movieboard.mboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class CommentDto {
    private int commentId;
    private int parentCommentId;
    private String commentWriter;
    private String commentPass;
    private String commentContent;
    private int postId;
}
