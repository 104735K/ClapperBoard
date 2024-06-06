package com.movieboard.mboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentDto {
    private int c_id;
    private String c_writer;
    private String c_content;
    private int community_id;
}
