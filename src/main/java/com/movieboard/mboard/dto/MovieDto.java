package com.movieboard.mboard.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private int m_id;
    private String m_writer;
    private byte[] m_poster;
    private String m_title;
    private int m_yor;
    private String m_director;
    private String m_actor;
    private String m_genre;
    private int m_rating;
    private String m_content;

}
