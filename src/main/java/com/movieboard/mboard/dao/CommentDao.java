package com.movieboard.mboard.dao;

import com.movieboard.mboard.dto.CommentDto;
import com.movieboard.mboard.util.DBUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CommentDao {
    private Connection connection;

    public CommentDao() throws SQLException {
        connection = DBUtil.getConnection();
    }

    public void createComment(CommentDto commentDTO) throws SQLException {
        String query = "INSERT INTO comments (postId, commentWriter, commentPass, commentContent) VALUES (?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, commentDTO.getPostId());
            preparedStatement.setString(2, commentDTO.getCommentWriter());
            preparedStatement.setString(3, commentDTO.getCommentPass());
            preparedStatement.setString(4, commentDTO.getCommentContent());
            preparedStatement.executeUpdate();
        }
    }

    public List<CommentDto> getCommentsByPostId(int postId) throws SQLException {
        List<CommentDto> commentDtoList = new ArrayList<>();
        String query = "SELECT * FROM comments WHERE postId=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, postId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    CommentDto commentDto = new CommentDto();
                    commentDto.setCommentId(resultSet.getInt("commentId"));
                    commentDto.setPostId(resultSet.getInt("postId"));
                    commentDto.setCommentWriter(resultSet.getString("commentWriter"));
                    commentDto.setCommentPass(resultSet.getString("commentPass"));
                    commentDto.setCommentContent(resultSet.getString("commentContent"));
                    commentDtoList.add(commentDto);
                }
            }
        }
            return commentDtoList;
    }

    public CommentDto updateComment(CommentDto commentDto) throws SQLException {
        String query = "UPDATE comments SET commentContent = ? WHERE commentId =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,commentDto.getCommentContent());
            preparedStatement.setInt(2,commentDto.getCommentId());
            preparedStatement.executeUpdate();
        }
        return commentDto;
    }

    public void deleteComment(int commentId) throws SQLException {
        String query = "DELETE FROM comments WHERE commentId=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, commentId);
            preparedStatement.executeUpdate();
        }
    }

    public CommentDto getCommentById(int commentId) throws SQLException {
        List<CommentDto> commentDtoList = new ArrayList<>();
        String query = "SELECT * FROM comments WHERE commentId=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, commentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    CommentDto commentDto = new CommentDto();
                    commentDto.setCommentId(resultSet.getInt("commentId"));
                    commentDto.setPostId(resultSet.getInt("postId"));
                    commentDto.setCommentWriter(resultSet.getString("commentWriter"));
                    commentDto.setCommentPass(resultSet.getString("commentPass"));
                    commentDto.setCommentContent(resultSet.getString("commentContent"));
                    commentDtoList.add(commentDto);
                }
            }
        }
        return (CommentDto) commentDtoList;
    }
}

