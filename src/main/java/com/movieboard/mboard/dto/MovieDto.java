package com.movieboard.mboard.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private int mId;
    private String mWriter;
    private String mPass;
    private byte[] mPoster;
    private String mTitle;
    private int mYor;
    private String mDirector;
    private String mActor;
    private String mGenre;
    private boolean mSpo;
    private int mRating;
    private String mContent;
    private String mImg;
}
