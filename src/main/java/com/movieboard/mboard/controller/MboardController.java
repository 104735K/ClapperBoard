package com.movieboard.mboard.controller;
import com.movieboard.mboard.dao.MovieDao;
import com.movieboard.mboard.dto.MovieDto;
import org.springframework.ui.Model;
import com.movieboard.mboard.dao.PostDao;
import com.movieboard.mboard.dto.PostDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/mboard")
public class MboardController {

    private PostDao postDAO;
    private MovieDao movieDao;

// 자유게시판
    @GetMapping("/community")
    public String community(Model model) {
        List<PostDto> postDtoList = new ArrayList<>();
        if (postDAO != null) {
            postDtoList = postDAO.getAllPosts();
        }
        model.addAttribute("postList", postDtoList);
        return "community";
    }

    @GetMapping("/community/save")
    public String save() {
        return "csave";
    }

    @PostMapping("/community/save")
    public String save(@ModelAttribute PostDto postDTO) throws SQLException {
        postDAO = new PostDao();
        System.out.println("PostDTO = " + postDTO);
        postDAO.createPost(postDTO);
        return "redirect:/mboard/community";
    }

    @GetMapping("/community/{user_id}")
    public String findById(@PathVariable int user_id, Model model) throws SQLException {
        Optional<PostDto> postDTOOptional = postDAO.getPostById(user_id);
        if (postDTOOptional.isPresent()) {
            PostDto postDTO = postDTOOptional.get();
            model.addAttribute("post", postDTO);
            return "detail";
        } else {
            return "community";
        }
    }

    @GetMapping("/community/update/{user_id}")
    public String updateForm(@PathVariable int user_id, Model model) throws SQLException {
        PostDao postDao1 = new PostDao();
        Optional<PostDto> postDTO = postDao1.getPostById(user_id);
        postDTO.ifPresent(dto -> model.addAttribute("updatePost", dto));
        return "update";
    }

    @PostMapping("/community/update")
    public String update(@ModelAttribute PostDto postDTO, Model model) throws SQLException {
        PostDto postDto1 = postDAO.updatePost(postDTO);
        if (postDto1 != null) {
            model.addAttribute("post", postDto1);
        }             return "detail";
    }

    @GetMapping("/community/delete/{user_id}")
    public String delete (@PathVariable int user_id) throws SQLException {
        postDAO.deletePost(user_id);
        return "redirect:/mboard/community";
    }

// 영화게사판
    @GetMapping("/movie")
        public String movie (Model model) {
            List<MovieDto> movieDtoList = new ArrayList<>();
            if (movieDao != null) {
                movieDtoList = movieDao.getAllMovies();
            }
            model.addAttribute("movieList", movieDtoList);
        return "movie";
    }
    @GetMapping("/movie/save")
    public String movieSave() {
        return "msave";
    }

    @PostMapping("/movie/save")
    public String movieSave(@ModelAttribute MovieDto movieDto) throws SQLException, IOException {
        movieDao = new MovieDao();
        System.out.println("MovieDto = " + movieDto);
        movieDao.createMpost(movieDto);
        return "redirect:/mboard/movie";
    }
    @GetMapping("/movie/{m_id}")
    public String findMovieById(@PathVariable int m_id, Model model) throws SQLException {
        Optional<MovieDto> movieDto = movieDao.getMovieById(m_id);
        if (movieDto.isPresent()) {
            MovieDto movieDto1 = movieDto.get();
            model.addAttribute("movie", movieDto1);
            return "mdetail";
        } else {
            return "movie";
        }
    }
}


