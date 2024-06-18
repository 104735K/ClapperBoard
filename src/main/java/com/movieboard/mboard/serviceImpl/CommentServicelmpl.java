package com.movieboard.mboard.serviceImpl;

import com.movieboard.mboard.dao.CommentDao;
import com.movieboard.mboard.dto.CommentDto;
import com.movieboard.mboard.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServicelmpl implements CommentService {
    private final CommentDao commentDao;


    @Override
    public void save(CommentDto commentDTO) throws SQLException {
        commentDao.createComment(commentDTO);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(int postId) throws SQLException {
        return commentDao.getCommentsByPostId(postId);
    }

    @Override
    public Optional<CommentDto> updateComment(CommentDto commentDto) throws SQLException {
        commentDao.updateComment(commentDto);
        return null;
    }

    @Override
    public void deleteComment(int commentId) throws SQLException {
        commentDao.deleteComment(commentId);
    }

    @Override
    public CommentDto getCommentById(int commentId) throws SQLException {
        return commentDao.getCommentById(commentId);
    }
}
