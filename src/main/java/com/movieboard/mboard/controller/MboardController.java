package com.movieboard.mboard.controller;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.movieboard.mboard.dao.MovieDao;
import com.movieboard.mboard.dto.MovieDto;
import com.mysql.cj.jdbc.Blob;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import com.movieboard.mboard.dao.PostDao;
import com.movieboard.mboard.dto.PostDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.movieboard.mboard.service.MboardService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.BitSet;
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
        System.out.println("qw");

        return "msave";
    }

    @PostMapping("/movie/save")
    public String movieSave(@RequestParam("m_writer") String m_writer,
                            @RequestParam("m_pass") String m_pass,
                            @RequestParam("m_poster") MultipartFile m_poster,
                            @RequestParam("m_title") String m_title,
                            @RequestParam("m_yor") int m_yor,
                            @RequestParam("m_director") String m_director,
                            @RequestParam("m_actor") String m_actor,
                            @RequestParam("m_spo") String m_spo,
                            @RequestParam("m_genre") String m_genre,
                            @RequestParam("m_rating") int m_rating,
                            @RequestParam("m_content") String m_content) throws SQLException, IOException {
        byte[] posterBytes = m_poster.getBytes();

        boolean is_spoiler = Boolean.parseBoolean(m_spo);

        MovieDto movieDto = new MovieDto();
        movieDto.setM_writer(m_writer);
        movieDto.setM_pass(m_pass);
        movieDto.setM_poster(posterBytes);
        movieDto.setM_title(m_title);
        movieDto.setM_yor(m_yor);
        movieDto.setM_director(m_director);
        movieDto.setM_actor(m_actor);
        movieDto.setM_genre(m_genre);
        movieDto.setM_spo(is_spoiler);
        movieDto.setM_rating(m_rating);
        movieDto.setM_content(m_content);

        mboardService.insertMovie(movieDto);
        return "redirect:/mboard/movie";
    }
    @GetMapping("/movie/{m_id}")
    public String findMovieById(@PathVariable int m_id, Model model) throws SQLException {
        Optional<MovieDto> movieDto = mboardService.getMovieById(m_id);
        if (movieDto.isPresent()) {
            MovieDto movieDto1 = movieDto.get();
            byte[] test = movieDto.get().getM_poster();
            movieDto.get().setM_img(Base64.encodeBase64String(test));
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
    public String movieUpdate(@RequestParam("m_writer") String m_writer,
                              @RequestParam("m_poster") MultipartFile m_poster,
                              @RequestParam("m_title") String m_title,
                              @RequestParam("m_yor") int m_yor,
                              @RequestParam("m_director") String m_director,
                              @RequestParam("m_actor") String m_actor,
                              @RequestParam("m_spo") String m_spo,
                              @RequestParam("m_genre") String m_genre,
                              @RequestParam("m_rating") int m_rating,
                              @RequestParam("m_content") String m_content,
                              @RequestParam("m_id") int m_id ) throws SQLException, IOException {
        byte[] posterBytes = null;
        if (!m_poster.isEmpty()) {
            posterBytes = m_poster.getBytes();
        }

        boolean is_spoiler = Boolean.parseBoolean(m_spo);


        MovieDto movieDto = new MovieDto();
        movieDto.setM_writer(m_writer);
        movieDto.setM_poster(posterBytes);
        movieDto.setM_title(m_title);
        movieDto.setM_yor(m_yor);
        movieDto.setM_director(m_director);
        movieDto.setM_actor(m_actor);
        movieDto.setM_genre(m_genre);
        movieDto.setM_spo(is_spoiler);
        movieDto.setM_rating(m_rating);
        movieDto.setM_content(m_content);
        movieDto.setM_id(m_id);

        mboardService.updateMovie(movieDto);
        return "redirect:/mboard/movie";
    }
    @GetMapping("/movie/delete/{m_id}")
    public String deleteMovie (@PathVariable int m_id) throws SQLException {
        mboardService.deleteMovie(m_id);
        return "redirect:/mboard/movie";
    }

}


