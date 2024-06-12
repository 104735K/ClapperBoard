package com.movieboard.mboard.service;

import com.movieboard.mboard.dto.MovieDto;
import com.movieboard.mboard.dto.PostDto;
import org.springframework.ui.Model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface MboardService {

    List<PostDto> selectPost();
    void insertPost(PostDto postDTO) throws SQLException;
    Optional<PostDto> getPostById(int postId) throws SQLException;
    void updatePost(PostDto postDto) throws SQLException;
    void deletePost(int postId) throws SQLException;
    List<MovieDto> selectMovie();
    void insertMovie(MovieDto movieDto) throws SQLException, IOException;
    Optional<MovieDto> getMovieById(int mId) throws SQLException;
    void updateMovie(MovieDto movieDto) throws SQLException;
    void deleteMovie(int mId) throws SQLException;
}
