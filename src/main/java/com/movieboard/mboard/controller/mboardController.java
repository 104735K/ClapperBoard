package com.movieboard.mboard.controller;
import org.springframework.boot.Banner;
import org.springframework.ui.Model;
import com.movieboard.mboard.dao.PostDAO;
import com.movieboard.mboard.dto.PostDTO;
import jakarta.annotation.Priority;
import jakarta.annotation.Resource;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.PSource;
import javax.management.monitor.MonitorSettingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/mboard")
public class mboardController {

    private PostDAO postDAO;

    @GetMapping("/community")
    public String community(Model model) {
        List<PostDTO> postDTOList = new ArrayList<>();
        if (postDAO != null) {
            postDTOList = postDAO.getAllPosts();
        }
        model.addAttribute("postList", postDTOList);
        return "community";
    }

    @GetMapping("/save")
    public String save() {
        return "csave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute PostDTO postDTO) throws SQLException {
        postDAO = new PostDAO();
        System.out.println("PostDTO = " + postDTO);
        postDAO.createPost(postDTO);
        return "redirect:/mboard/community";
    }

    @GetMapping("/{user_id}")
    public String findById(@PathVariable int user_id, Model model) throws SQLException {
        Optional<PostDTO> postDTOOptional = postDAO.getPostById(user_id);
        if (postDTOOptional.isPresent()) {
            PostDTO postDTO = postDTOOptional.get();
            model.addAttribute("post", postDTO);
            return "detail";
        } else {
            return "community";
        }
    }

    @GetMapping("/update/{user_id}")
    public String updateForm(@PathVariable int user_id, Model model) throws SQLException {
        PostDAO postDAO1 = new PostDAO();
        Optional<PostDTO> postDTO = postDAO1.getPostById(user_id);
        postDTO.ifPresent(dto -> model.addAttribute("updatePost", dto));
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute PostDTO postDTO, Model model) throws SQLException {
        PostDTO postDTO1 = postDAO.updatePost(postDTO);
        if (postDTO1 != null) {
            model.addAttribute("post", postDTO1);
        }             return "detail";
    }

    @GetMapping("/delete/{user_id}")
    public String delete (@PathVariable int user_id) throws SQLException {
        postDAO.deletePost(user_id);
        return "redirect:/mboard/community";
    }
}


