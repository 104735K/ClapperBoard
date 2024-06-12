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
        String query = "INSERT INTO movies (mId, mWriter, mPass, mPoster, mTitle, mYor, mDirector, mActor, mGenre, mSpo, mRating, mContent) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, movieDto.getMId());
            pstmt.setString(2, movieDto.getMWriter());
            pstmt.setString(3,movieDto.getMPass());

            if (movieDto.getMPoster() != null) {
                pstmt.setBytes(4, movieDto.getMPoster());
            } else {
                pstmt.setNull(4, Types.BLOB);
            }
            pstmt.setString(5, movieDto.getMTitle());
            pstmt.setInt(6, movieDto.getMYor());
            pstmt.setString(7, movieDto.getMDirector());
            pstmt.setString(8, movieDto.getMActor());
            pstmt.setString(9, movieDto.getMGenre());
            pstmt.setBoolean(10,movieDto.isMSpo());
            pstmt.setInt(11, movieDto.getMRating());
            pstmt.setString(12, movieDto.getMContent());
            pstmt.executeUpdate();

        }
    }

    public List<MovieDto> getAllMovies() {
        List<MovieDto> movieDtoList = new ArrayList<>();
        String query = "SELECT * FROM movies ORDER BY mId DESC";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MovieDto movieDto = new MovieDto();
                movieDto.setMId(resultSet.getInt("mId"));
                movieDto.setMWriter(resultSet.getString("mWriter"));
                movieDto.setMTitle(resultSet.getString("mTitle"));
                movieDto.setMSpo(resultSet.getBoolean("mSpo"));
                movieDtoList.add(movieDto);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return movieDtoList;

    }

    public Optional<MovieDto> getMovieById(int id) throws SQLException {
        String query = "SELECT * FROM movies WHERE mId =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    MovieDto movieDto = new MovieDto();
                    movieDto.setMId(resultSet.getInt("mId"));
                    movieDto.setMWriter(resultSet.getString("mWriter"));
                    movieDto.setMPass(resultSet.getString("mPass"));
                    movieDto.setMPoster(resultSet.getBytes("mPoster"));
                    movieDto.setMTitle(resultSet.getString("mTitle"));
                    movieDto.setMYor(resultSet.getInt("mYor"));
                    movieDto.setMDirector(resultSet.getString("mDirector"));
                    movieDto.setMActor(resultSet.getString("mActor"));
                    movieDto.setMGenre(resultSet.getString("mGenre"));
                    movieDto.setMSpo(resultSet.getBoolean("mSpo"));
                    movieDto.setMRating(resultSet.getInt("mRating"));
                    movieDto.setMContent(resultSet.getString("mContent"));
                    return Optional.of(movieDto);
                }

            }
        }
        return Optional.empty();
    }
    public MovieDto updateMovie (MovieDto movieDto) throws SQLException {
        String query = "UPDATE movies SET  mPoster=?, mTitle =?, mYor=?, mDirector=?, mActor=?, mGenre=?, mSpo=?, mRating=?, mContent=? WHERE mId=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setBytes(1, movieDto.getMPoster());
            preparedStatement.setString(2, movieDto.getMTitle());
            preparedStatement.setInt(3, movieDto.getMYor());
            preparedStatement.setString(4, movieDto.getMDirector());
            preparedStatement.setString(5, movieDto.getMActor());
            preparedStatement.setString(6, movieDto.getMGenre());
            preparedStatement.setBoolean(7, movieDto.isMSpo());
            preparedStatement.setInt(8, movieDto.getMRating());
            preparedStatement.setString(9, movieDto.getMContent());
            preparedStatement.setInt(10, movieDto.getMId());
            preparedStatement.executeUpdate();
        }
        return movieDto;
    }

    public void deleteMovie(int mId) throws SQLException {
        String query ="DELETE FROM movies WHERE mId=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, mId);
            preparedStatement.executeUpdate();
        }
    }

}
