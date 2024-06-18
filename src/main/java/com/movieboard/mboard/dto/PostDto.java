package com.movieboard.mboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private int postId;
    private String postWriter;
    private String postPass;
    private String postTitle;
    private String postContent;
}
