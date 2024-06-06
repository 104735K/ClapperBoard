package com.movieboard.mboard.controller;

import com.movieboard.mboard.dao.CommentDao;
import com.movieboard.mboard.dto.CommentDto;
import com.movieboard.mboard.service.CommentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private CommentDao commentDao;
    private CommentService commentService;

    public CommentController(CommentDao commentDao, CommentService commentService) {
        this.commentDao = commentDao;
        this.commentService = commentService;
    }

    @GetMapping("/list")
    public String comments(Model model) {
        List<CommentDto> commentDtoList = commentService.findComment();
        model.addAttribute("commentList", commentDtoList);
        return "detail";
    }
    @PostMapping("/save")
    public String save(@ModelAttribute CommentDto commentDto) throws SQLException {
        commentService.save(commentDto);
        return "redirect:/mboard/community/detail";
    }
}
