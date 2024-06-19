package com.movieboard.mboard.controller;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.movieboard.mboard.dao.MovieDao;
import com.movieboard.mboard.dto.CommentDto;
import com.movieboard.mboard.dto.MovieDto;
import com.movieboard.mboard.service.CommentService;
import com.mysql.cj.jdbc.Blob;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class MboardController {

    private PostDao postDao;
    private MovieDao movieDao;
    private MboardService mboardService;
    private CommentService commentService;

// 자유게시판
    @GetMapping("/community")
    public String community(Model model) {
        List<PostDto> postDtoList = mboardService.selectPost();
        model.addAttribute("postList", postDtoList);
        return "community";
    }

    @GetMapping("/community/new")
    public String showSaveForm() {
        return "csave";
    }

    @PostMapping("/community/save")
    public String save(@ModelAttribute PostDto postDTO) throws SQLException {
        mboardService.insertPost(postDTO);
        return "redirect:/mboard/community";
    }

    @GetMapping("/community/{postId}")
    public String findById(@PathVariable int postId, Model model,@ModelAttribute CommentDto commentDto) throws SQLException {
        Optional<PostDto> postDto = mboardService.getPostById(postId);
        if (postDto.isPresent()) {
            PostDto postDto1 = postDto.get();
            model.addAttribute("post", postDto1);
            List<CommentDto> commentDtoList = commentService.getCommentsByPostId(postId);
            model.addAttribute("commentList", commentDtoList);
        }
        return "detail";
    }

    @GetMapping("/community/update/{postId}")
    public String updateForm(@PathVariable int postId, Model model) throws SQLException {
        Optional<PostDto> postDTO = mboardService.getPostById(postId);
        postDTO.ifPresent(dto -> model.addAttribute("updatePost", dto));
        return "update";
    }

    @PostMapping("/community/update")
    public String update(@ModelAttribute PostDto postDTO) throws SQLException {
        mboardService.updatePost(postDTO);
        return "redirect:/mboard/community";
    }

    @GetMapping("/community/delete/{postId}")
    public String delete (@PathVariable int postId) throws SQLException {
        mboardService.deletePost(postId);
        return "redirect:/mboard/community";
    }

// 영화게사판
    @GetMapping("/movie")
    public String movie (Model model) {
        List<MovieDto> movieDtoList = mboardService.selectMovie();
        model.addAttribute("movieList", movieDtoList);
        return "movie";
    }
    @GetMapping("/movie/new")
    public String showMovieSaveForm() {
        System.out.println("qw");
        return "msave";
    }

    @PostMapping("/movie/save")
    public String movieSave(@RequestParam("mWriter") String mWriter,
                            @RequestParam("mPass") String mPass,
                            @RequestParam("mPoster") MultipartFile mPoster,
                            @RequestParam("mTitle") String mTitle,
                            @RequestParam("mYor") int mYor,
                            @RequestParam("mDirector") String mDirector,
                            @RequestParam("mActor") String mActor,
                            @RequestParam("mSpo") String mSpo,
                            @RequestParam("mGenre") String mGenre,
                            @RequestParam("mRating") int mRating,
                            @RequestParam("mContent") String mContent) throws SQLException, IOException {
        byte[] posterBytes = mPoster.getBytes();

        boolean is_spoiler = Boolean.parseBoolean(mSpo);

        MovieDto movieDto = new MovieDto();
        movieDto.setMWriter(mWriter);
        movieDto.setMPass(mPass);
        movieDto.setMPoster(posterBytes);
        movieDto.setMTitle(mTitle);
        movieDto.setMYor(mYor);
        movieDto.setMDirector(mDirector);
        movieDto.setMActor(mActor);
        movieDto.setMGenre(mGenre);
        movieDto.setMSpo(is_spoiler);
        movieDto.setMRating(mRating);
        movieDto.setMContent(mContent);

        mboardService.insertMovie(movieDto);
        return "redirect:/mboard/movie";
    }
    @GetMapping("/movie/{mId}")
    public String findMovieById(@PathVariable int mId, Model model) throws SQLException {
        Optional<MovieDto> movieDto = mboardService.getMovieById(mId);
        if (movieDto.isPresent()) {
            MovieDto movieDto1 = movieDto.get();
            byte[] test = movieDto.get().getMPoster();
            movieDto.get().setMImg(Base64.encodeBase64String(test));
            model.addAttribute("movie", movieDto1);

        }

        return "mdetail";

    }
    @GetMapping("/movie/update/{mId}")
    public String updateMovieForm(@PathVariable int mId, Model model) throws SQLException {
        Optional<MovieDto> movieDto = mboardService.getMovieById(mId);
        movieDto.ifPresent(dto -> model.addAttribute("updateMovie", dto));
        return "mupdate";
    }

    @PostMapping("/movie/update")
    public String movieUpdate(@RequestParam("mWriter") String mWriter,
                              @RequestParam("mPoster") MultipartFile mPoster,
                              @RequestParam("mTitle") String mTitle,
                              @RequestParam("mYor") int mYor,
                              @RequestParam("mDirector") String mDirector,
                              @RequestParam("mActor") String mActor,
                              @RequestParam("mSpo") String mSpo,
                              @RequestParam("mGenre") String mGenre,
                              @RequestParam("mRating") int mRating,
                              @RequestParam("mContent") String mContent,
                              @RequestParam("mId") int mId ) throws SQLException, IOException {
        byte[] posterBytes = null;
        if (!mPoster.isEmpty()) {
            posterBytes = mPoster.getBytes();
        }

        boolean is_spoiler = Boolean.parseBoolean(mSpo);


        MovieDto movieDto = new MovieDto();
        movieDto.setMWriter(mWriter);
        movieDto.setMPoster(posterBytes);
        movieDto.setMTitle(mTitle);
        movieDto.setMYor(mYor);
        movieDto.setMDirector(mDirector);
        movieDto.setMActor(mActor);
        movieDto.setMGenre(mGenre);
        movieDto.setMSpo(is_spoiler);
        movieDto.setMRating(mRating);
        movieDto.setMContent(mContent);
        movieDto.setMId(mId);

        mboardService.updateMovie(movieDto);
        return "redirect:/mboard/movie";
    }
    @GetMapping("/movie/delete/{mId}")
    public String deleteMovie (@PathVariable int mId) throws SQLException {
        mboardService.deleteMovie(mId);
        return "redirect:/mboard/movie";
    }

}


