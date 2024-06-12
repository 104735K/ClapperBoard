package com.movieboard.mboard.serviceImpl;

import com.movieboard.mboard.dao.MovieDao;
import com.movieboard.mboard.dao.PostDao;
import com.movieboard.mboard.dto.CommentDto;
import com.movieboard.mboard.dto.MovieDto;
import com.movieboard.mboard.dto.PostDto;
import com.movieboard.mboard.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.movieboard.mboard.service.MboardService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public  class MboardServiceImpl implements MboardService {
    private PostDao postDao;
    private MovieDao movieDao;
    private CommentService commentService;

    @Override
    public List<PostDto> selectPost() {
        return postDao.getAllPosts();
    }

    @Override
    public void insertPost(PostDto postDto) throws SQLException {
        postDao.createPost(postDto);

    }

    @Override
    public Optional<PostDto> getPostById(int postId) {
        try {
            Optional<PostDto> optionalPostDto = postDao.getPostById(postId);
            optionalPostDto.ifPresent(postDto -> {
                try {
                    List<CommentDto> comments = commentService.getCommentsByPostId(postId);
                    postDto.setPostComment(comments);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            return optionalPostDto;
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
    
    @Override
    public void updatePost(PostDto postDto) throws SQLException {
        postDao.updatePost(postDto);
    }

    @Override
    public void deletePost(int postId) throws SQLException {
        postDao.deletePost(postId);

    }

    @Override
    public List<MovieDto> selectMovie() {
        return movieDao.getAllMovies();
    }

    @Override
    public void insertMovie(MovieDto movieDto) throws SQLException, IOException {
        movieDao.createMovie(movieDto);

    }

    @Override
    public Optional<MovieDto> getMovieById(int mId) throws SQLException {
        return movieDao.getMovieById(mId);
    }

    @Override
    public void updateMovie(MovieDto movieDto) throws SQLException {
        movieDao.updateMovie(movieDto);

    }

    @Override
    public void deleteMovie(int mId) throws SQLException {
        movieDao.deleteMovie(mId);

    }
}
