package com.movieboard.mboard.dao;

import com.fasterxml.jackson.databind.ser.Serializers;
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
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class MovieDao {
    private Connection connection;

    public MovieDao() throws SQLException {
        connection = DBUtil.getConnection();
    }

    public void createMovie(MovieDto movieDto) throws SQLException, IOException {
        String query = "INSERT INTO movies (m_id, m_writer, m_pass, m_poster, m_title, m_yor, m_director, m_actor, m_genre, m_spo, m_rating, m_content) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, movieDto.getM_id());
            pstmt.setString(2, movieDto.getM_writer());
            pstmt.setString(3,movieDto.getM_pass());

            if (movieDto.getM_poster() != null) {
                pstmt.setBytes(4, movieDto.getM_poster());
            } else {
                pstmt.setNull(4, Types.BLOB);
            }
            pstmt.setString(5, movieDto.getM_title());
            pstmt.setInt(6, movieDto.getM_yor());
            pstmt.setString(7, movieDto.getM_director());
            pstmt.setString(8, movieDto.getM_actor());
            pstmt.setString(9, movieDto.getM_genre());
            pstmt.setBoolean(10,movieDto.isM_spo());
            pstmt.setInt(11, movieDto.getM_rating());
            pstmt.setString(12, movieDto.getM_content());
            pstmt.executeUpdate();

        }
    }

    public List<MovieDto> getAllMovies() {
        List<MovieDto> movieDtoList = new ArrayList<>();
        String query = "SELECT * FROM movies ORDER BY m_id DESC";
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
                        resultSet.getString("m_pass"),
                        m_posterBytes,
                        resultSet.getString("m_title"),
                        resultSet.getInt("m_yor"),
                        resultSet.getString("m_director"),
                        resultSet.getString("m_actor"),
                        resultSet.getString("m_genre"),
                        resultSet.getBoolean("m_spo"),
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
                    movieDto.setM_pass(resultSet.getString("m_pass"));
                    movieDto.setM_poster(resultSet.getBytes("m_poster"));
                    movieDto.setM_title(resultSet.getString("m_title"));
                    movieDto.setM_yor(resultSet.getInt("m_yor"));
                    movieDto.setM_director(resultSet.getString("m_director"));
                    movieDto.setM_actor(resultSet.getString("m_actor"));
                    movieDto.setM_genre(resultSet.getString("m_genre"));
                    movieDto.setM_spo(resultSet.getBoolean("m_spo"));
                    movieDto.setM_rating(resultSet.getInt("m_rating"));
                    movieDto.setM_content(resultSet.getString("m_content"));
                    return Optional.of(movieDto);
                }

            }
        }
        return Optional.empty();
    }
    public MovieDto updateMovie (MovieDto movieDto) throws SQLException {
        String query = "UPDATE movies SET m_writer =?, m_title =?, m_yor=?, m_director=?, m_actor=?, m_genre=?, m_spo=?, m_rating=?, m_content=? WHERE m_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, movieDto.getM_writer());
            preparedStatement.setString(2, movieDto.getM_title());
            preparedStatement.setInt(3, movieDto.getM_yor());
            preparedStatement.setString(4, movieDto.getM_director());
            preparedStatement.setString(5, movieDto.getM_actor());
            preparedStatement.setString(6, movieDto.getM_genre());
            preparedStatement.setBoolean(7, movieDto.isM_spo());
            preparedStatement.setInt(8, movieDto.getM_rating());
            preparedStatement.setString(9, movieDto.getM_content());
            preparedStatement.setInt(10, movieDto.getM_id());
            preparedStatement.executeUpdate();
        }
        return movieDto;
    }

    public void deleteMovie(int m_id) throws SQLException {
        String query ="DELETE FROM movies WHERE m_id=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, m_id);
            preparedStatement.executeUpdate();
        }
    }

}
