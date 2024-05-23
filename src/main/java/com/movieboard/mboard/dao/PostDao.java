package com.movieboard.mboard.dao;

import com.movieboard.mboard.dto.PostDto;
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
public class PostDao {
    private Connection connection;

    public PostDao() throws SQLException {
        connection = DBUtil.getConnection();
    }

    public void createPost(PostDto postDTO) throws SQLException {
        String query = "INSERT INTO posts (user_id, user_name, title, content) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, postDTO.getUser_id());
            pstmt.setString(2, postDTO.getUser_name());
            pstmt.setString(3, postDTO.getTitle());
            pstmt.setString(4, postDTO.getContent());
            pstmt.executeUpdate();
        }
    }

    public List<PostDto> getAllPosts() {
        List<PostDto> postDtoList = new ArrayList<>();
        String query = "SELECT * FROM posts";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PostDto postDTO = new PostDto(
                        resultSet.getInt("user_id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("title"),
                        resultSet.getString("content")
                );
                postDtoList.add(postDTO);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return postDtoList;
    }

    public Optional<PostDto> getPostById(int id) throws SQLException {
        String query = "SELECT * FROM posts WHERE user_id =?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    PostDto postDTO = new PostDto();
                    postDTO.setUser_id(resultSet.getInt("user_id"));
                    postDTO.setUser_name(resultSet.getString("user_name"));
                    postDTO.setTitle(resultSet.getString("title"));
                    postDTO.setContent(resultSet.getString("content"));
                    return Optional.of(postDTO);
                }
            }
        } return Optional.empty();
    }

    public PostDto updatePost(PostDto postDTO) throws SQLException {
        String query = "UPDATE posts SET user_name = ?, title = ?, content =? WHERE user_id =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1,postDTO.getUser_name());
            preparedStatement.setString(2, postDTO.getTitle());
            preparedStatement.setString(3, postDTO.getContent());
            preparedStatement.setInt(4, postDTO.getUser_id());
            preparedStatement.executeUpdate();
        }
        return postDTO;
    }

    public void deletePost(int post_id) throws SQLException {
        String query = "DELETE FROM posts WHERE user_id =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, post_id);
            preparedStatement.executeUpdate();
        }
    }
}
