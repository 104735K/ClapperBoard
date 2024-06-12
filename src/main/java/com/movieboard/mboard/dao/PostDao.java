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
        String query = "INSERT INTO posts (postId, postWriter, postPass, postTitle, postContent) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, postDTO.getPostId());
            pstmt.setString(2, postDTO.getPostWriter());
            pstmt.setString(3, postDTO.getPostPass());
            pstmt.setString(4, postDTO.getPostTitle());
            pstmt.setString(5, postDTO.getPostContent());
            pstmt.executeUpdate();
        }
    }

    public List<PostDto> getAllPosts() {
        List<PostDto> postDtoList = new ArrayList<>();
        String query = "SELECT * FROM posts ORDER BY postId DESC";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PostDto postDTO = new PostDto();
                    postDTO.setPostId(resultSet.getInt("postId"));
                    postDTO.setPostWriter(resultSet.getString("postWriter"));
                    postDTO.setPostPass(resultSet.getString("postPass"));
                    postDTO.setPostTitle(resultSet.getString("postTitle"));
                    postDTO.setPostContent(resultSet.getString("postContent"));
                    postDtoList.add(postDTO);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return postDtoList;
    }

    public Optional<PostDto> getPostById(int id) throws SQLException {
        String query = "SELECT * FROM posts WHERE postId =?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    PostDto postDTO = new PostDto();
                    postDTO.setPostId(resultSet.getInt("postId"));
                    postDTO.setPostWriter(resultSet.getString("postWriter"));
                    postDTO.setPostPass(resultSet.getString("postPass"));
                    postDTO.setPostTitle(resultSet.getString("postTitle"));
                    postDTO.setPostContent(resultSet.getString("postContent"));
                    return Optional.of(postDTO);
                }
            }
        } return Optional.empty();
    }

    public PostDto updatePost(PostDto postDTO) throws SQLException {
        String query = "UPDATE posts SET postTitle = ?, postContent =? WHERE postId =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, postDTO.getPostTitle());
            preparedStatement.setString(2, postDTO.getPostContent());
            preparedStatement.setInt(3, postDTO.getPostId());
            preparedStatement.executeUpdate();
        }
        return postDTO;
    }

    public void deletePost(int postId) throws SQLException {
        String query = "DELETE FROM posts WHERE postId =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, postId);
            preparedStatement.executeUpdate();
        }
    }
}
