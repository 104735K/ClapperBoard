package com.movieboard.mboard.service;

import com.movieboard.mboard.dto.CommentDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CommentService {
    void save (CommentDto commentDTO) throws SQLException;
    List<CommentDto> getCommentsByPostId(int postId) throws SQLException;
    Optional<CommentDto> updateComment(CommentDto commentDto) throws SQLException;
    void deleteComment(int commentId) throws SQLException;
    CommentDto getCommentById(int commentId) throws SQLException;

}
