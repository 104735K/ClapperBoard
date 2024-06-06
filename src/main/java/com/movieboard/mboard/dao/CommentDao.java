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

@Repository
@AllArgsConstructor
public class CommentDao {
    private Connection connection;

    public CommentDao() throws SQLException {
        connection = DBUtil.getConnection();
    }

    public void createComment(CommentDto commentDTO) throws SQLException {
        String query = "INSERT INTO comments (c_id, c_writer, c_content) VALUES (?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, commentDTO.getC_id());
            preparedStatement.setString(2, commentDTO.getC_writer());
            preparedStatement.setString(3, commentDTO.getC_content());
            preparedStatement.executeUpdate();
        }
    }

    public List<CommentDto> getAllComments() {
        List<CommentDto> commentDtoList = new ArrayList<>();
        String query = "SELECT * FROM comments";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CommentDto commentDto = new CommentDto();
                commentDto.setC_id(resultSet.getInt("c_id"));
                commentDto.setC_writer(resultSet.getString("c_writer"));
                commentDto.setC_content(resultSet.getString("c_content"));
                commentDtoList.add(commentDto);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return commentDtoList;
    }

    public void deleteComment(int c_id) throws SQLException {
        String query = "DELETE FROM commets WHERE c_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, c_id);
            preparedStatement.executeUpdate();
        }
    }

}
