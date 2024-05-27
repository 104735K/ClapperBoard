package com.movieboard.mboard.controller;
import com.movieboard.mboard.dao.MovieDao;
import com.movieboard.mboard.dto.MovieDto;
import com.movieboard.mboard.serviceImpl.MboardServiceImpl;
import io.micrometer.core.instrument.MockClock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.ui.Model;
import com.movieboard.mboard.dao.PostDao;
import com.movieboard.mboard.dto.PostDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.MboardService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/mboard")
public class MboardController {

    private PostDao postDao;
    private MovieDao movieDao;

    private MboardService mboardService;

    @Autowired
    public MboardController(PostDao postDao, MovieDao movieDao, MboardService mboardService) {
        this.postDao = postDao;
        this.movieDao = movieDao;
        this.mboardService = mboardService;
    }
// 자유게시판
    @GetMapping("/community")
    public String community(Model model) {
        List<PostDto> postDtoList = mboardService.selectPost();
        model.addAttribute("postList", postDtoList);
        return "community";
    }

    @GetMapping("/community/save")
    public String save() {
        return "csave";
    }

    @PostMapping("/community/save")
    public String save(@ModelAttribute PostDto postDTO) throws SQLException {
        mboardService.insertPost(postDTO);
        return "redirect:/mboard/community";
    }

    @GetMapping("/community/{user_id}")
    public String findById(@PathVariable int user_id, Model model) throws SQLException {
        Optional<PostDto> postDto = mboardService.getPostById(user_id);
        if (postDto.isPresent()) {
            PostDto postDto1 = postDto.get();
            model.addAttribute("post", postDto1);
        }
        return "detail";
    }

    @GetMapping("/community/update/{user_id}")
    public String updateForm(@PathVariable int user_id, Model model) throws SQLException {
        Optional<PostDto> postDTO = mboardService.getPostById(user_id);
        postDTO.ifPresent(dto -> model.addAttribute("updatePost", dto));
        return "update";
    }

    @PostMapping("/community/update")
    public String update(@ModelAttribute PostDto postDTO) throws SQLException {
        mboardService.updatePost(postDTO);
        return "redirect:/mboard/community";
    }

    @GetMapping("/community/delete/{user_id}")
    public String delete (@PathVariable int user_id) throws SQLException {
        mboardService.deletePost(user_id);
        return "redirect:/mboard/community";
    }

// 영화게사판
    @GetMapping("/movie")
    public String movie (Model model) {
        List<MovieDto> movieDtoList = mboardService.selectMovie();
        model.addAttribute("movieList", movieDtoList);
        return "movie";
    }
    @GetMapping("/movie/save")
    public String movieSave() {
        return "msave";
    }

    @PostMapping("/movie/save")
    public String movieSave(@RequestBody MovieDto movieDto) throws SQLException, IOException {
        mboardService.updateMovie(movieDto);
        return "redirect:/mboard/movie";
    }
    @GetMapping("/movie/{m_id}")
    public String findMovieById(@PathVariable int m_id, Model model) throws SQLException {
        Optional<MovieDto> movieDto = mboardService.getMovieById(m_id);
        if (movieDto.isPresent()) {
            MovieDto movieDto1 = movieDto.get();
            model.addAttribute("movie", movieDto1);
        }
            return "mdetail";

    }
    @GetMapping("/movie/update/{m_id}")
    public String updateMovieForm(@PathVariable int m_id, Model model) throws SQLException {
        Optional<MovieDto> movieDto = mboardService.getMovieById(m_id);
        movieDto.ifPresent(dto -> model.addAttribute("updateMovie", dto));
        return "mupdate";
    }

    @PostMapping("/movie/update")
    public String movieUpdate(@ModelAttribute MovieDto movieDto) throws SQLException {
        mboardService.updateMovie(movieDto);
        return "redirect:/mboard/movie";
    }
    @GetMapping("/movie/delete/{m_id}")
    public String deleteMovie (@PathVariable int m_id) throws SQLException {
        mboardService.deleteMovie(m_id);
        return "redirect:/mboard/movie";
    }

}


