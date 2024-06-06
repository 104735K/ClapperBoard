package com.movieboard.mboard.serviceImpl;

import com.movieboard.mboard.dao.CommentDao;
import com.movieboard.mboard.dto.CommentDto;
import com.movieboard.mboard.service.CommentService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
@Service
public class CommentServicelmpl implements CommentService {
    private CommentDao commentDao;

    public CommentServicelmpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public void save(CommentDto commentDTO) throws SQLException {
        commentDao.createComment(commentDTO);
    }

    @Override
    public List<CommentDto> findComment() {
        return commentDao.getAllComments();
    }
    @Override
    public void deleteComment(int c_id) throws SQLException {
        commentDao.deleteComment(c_id);
    }
}
