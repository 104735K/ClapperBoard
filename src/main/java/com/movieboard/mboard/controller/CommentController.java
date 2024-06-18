package com.movieboard.mboard.controller;

import com.movieboard.mboard.dao.CommentDao;
import com.movieboard.mboard.dto.CommentDto;
import com.movieboard.mboard.dto.PostDto;
import com.movieboard.mboard.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/mboard")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/community/comment/{postId}")
    public String comments(Model model, @PathVariable int postId) throws SQLException {
        List<CommentDto> commentDtoList = commentService.getCommentsByPostId(postId);
        model.addAttribute("commentList", commentDtoList);
        return "detail";
    }
    @PostMapping("/community/{postId}")
    public String save(@ModelAttribute CommentDto commentDto, @RequestParam("postId") int postId) throws SQLException {
        commentDto.setPostId(postId);
        commentService.save(commentDto);
        return "redirect:/mboard/community/" + postId;
    }

    @GetMapping("/community/comment/update/{commentId}")
    public String updateForm(@PathVariable int commentId, Model model) throws SQLException {
        CommentDto commentDto = commentService.getCommentById(commentId);
        model.addAttribute("updateComment", commentDto);
        return "editComment";
    }

    @PostMapping("/community/comment/update")
    public String updateComment(@ModelAttribute CommentDto commentDto) throws SQLException {
        commentService.updateComment(commentDto);
        return "redirect:/mboard/community/" + commentDto.getPostId();
    }

    @GetMapping("/community/comment/delete/{commentId}")
    public String deleteComment (@PathVariable int commentId, HttpServletRequest request) throws SQLException {
        commentService.deleteComment(commentId);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
