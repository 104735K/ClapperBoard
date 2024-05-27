package com.movieboard.mboard.serviceImpl;

import com.movieboard.mboard.dao.MovieDao;
import com.movieboard.mboard.dao.PostDao;
import com.movieboard.mboard.dto.MovieDto;
import com.movieboard.mboard.dto.PostDto;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import service.MboardService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public  class MboardServiceImpl implements MboardService {
    private PostDao postDao;
    private MovieDao movieDao;

    public MboardServiceImpl(PostDao postDao, MovieDao movieDao) {
        this.postDao = postDao;
        this.movieDao = movieDao;
    }

    @Override
    public List<PostDto> selectPost() {
        return postDao.getAllPosts();
    }

    @Override
    public void insertPost(PostDto postDto) throws SQLException {
        postDao.createPost(postDto);

    }

    @Override
    public Optional<PostDto> getPostById(int user_id) throws SQLException {
        return postDao.getPostById(user_id);
    }

    @Override
    public void updatePost(PostDto postDto) throws SQLException {
        postDao.updatePost(postDto);
    }

    @Override
    public void deletePost(int user_id) throws SQLException {
        postDao.deletePost(user_id);

    }

    @Override
    public List<MovieDto> selectMovie() {
        return movieDao.getAllMovies();
    }

    @Override
    public void insertMovie(MovieDto movieDto) throws SQLException, IOException {
        movieDao.createMpost(movieDto);

    }

    @Override
    public Optional<MovieDto> getMovieById(int m_id) throws SQLException {
        return movieDao.getMovieById(m_id);
    }

    @Override
    public void updateMovie(MovieDto movieDto) throws SQLException {
        movieDao.updateMovie(movieDto);

    }

    @Override
    public void deleteMovie(int m_id) throws SQLException {
        movieDao.deleteMovie(m_id);

    }
}
