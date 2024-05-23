package com.movieboard.mboard.dao;

import com.movieboard.mboard.dto.MovieDto;
import com.movieboard.mboard.util.DBUtil;
import com.mysql.cj.jdbc.integration.c3p0.MysqlConnectionTester;
import com.mysql.cj.jdbc.result.UpdatableResultSet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class MovieDao {
    private Connection connection;

    public MovieDao() throws SQLException {
        connection = DBUtil.getConnection();
    }

    public void createMpost(MovieDto movieDto) throws SQLException, IOException {
        String query = "INSERT INTO movies (m_id, m_writer, m_poster, m_title, m_yor, m_director, m_actor, m_genre, m_rating, m_content) VALUES (?, ?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, movieDto.getM_id());
            pstmt.setString(2, movieDto.getM_writer());
            pstmt.setString(4, movieDto.getM_title());
            pstmt.setInt(5, movieDto.getM_yor());
            pstmt.setString(6, movieDto.getM_director());
            pstmt.setString(7, movieDto.getM_actor());
            pstmt.setString(8, movieDto.getM_genre());
            pstmt.setInt(9, movieDto.getM_rating());
            pstmt.setString(10, movieDto.getM_content());
            pstmt.executeUpdate();

        }
    }

    public List<MovieDto> getAllMovies() {
        List<MovieDto> movieDtoList = new ArrayList<>();
        String query = "SELECT * FROM movies";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Blob m_posterBlob = resultSet.getBlob("m_poster");
                byte[] m_posterBytes = null;
                if (m_posterBlob != null) {
                    m_posterBytes = m_posterBlob.getBytes(1,(int) m_posterBlob.length());
                }
                MovieDto movieDto = new MovieDto(
                        resultSet.getInt("m_id"),
                        resultSet.getString("m_writer"),
                        m_posterBytes,
                        resultSet.getString("m_title"),
                        resultSet.getInt("m_yor"),
                        resultSet.getString("m_director"),
                        resultSet.getString("m_actor"),
                        resultSet.getString("m_genre"),
                        resultSet.getInt("m_rating"),
                        resultSet.getString("m_content")
                );
                movieDtoList.add(movieDto);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return movieDtoList;

    }

    public Optional<MovieDto> getMovieById(int id) throws SQLException {
        String query = "SELECT * FROM movies WHERE m_id =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    MovieDto movieDto = new MovieDto();
                    movieDto.setM_id(resultSet.getInt("m_id"));
                    movieDto.setM_writer(resultSet.getString("m_writer"));
                    movieDto.setM_poster(resultSet.getBytes("m_poster"));
                    movieDto.setM_title(resultSet.getString("m_title"));
                    movieDto.setM_yor(resultSet.getInt("m_yor"));
                    movieDto.setM_director(resultSet.getString("m_director"));
                    movieDto.setM_actor(resultSet.getString("m_actor"));
                    movieDto.setM_genre(resultSet.getString("m_genre"));
                    movieDto.setM_rating(resultSet.getInt("m_rating"));
                    movieDto.setM_content(resultSet.getString("m_content"));
                    return Optional.of(movieDto);
                }

            }
        }
        return Optional.empty();
    }

}
