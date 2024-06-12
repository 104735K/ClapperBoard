package com.movieboard.mboard.service;

import com.movieboard.mboard.dto.CommentDto;

import java.sql.SQLException;
import java.util.List;

public interface CommentService {
    void save (CommentDto commentDTO) throws SQLException;

    List<CommentDto> getCommentsByPostId(int postId) throws SQLException;

    void deleteComment(int commentId) throws SQLException;
}
