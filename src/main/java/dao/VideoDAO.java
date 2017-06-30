package dao;

import model.User;
import model.Video;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */
public class VideoDAO {
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addVideo(int user_id, String path) throws FileNotFoundException {
        String sql = "insert into video(user_id, path) values(?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, user_id);
                ps.setString(2, path);
                return ps;
            }
        }, keyHolder);

        int generatedId = keyHolder.getKey().intValue();
        return generatedId;
    }

    public List<Video> getVideoByID(int id) throws IOException {
        String sql = "select * from video where id = ?";

        return jdbcTemplate.query(sql,new Object[]{id},new VideoDAO.VideoRowMapper());
    }

    public void updateVideo(int id, int user_id, String path) throws FileNotFoundException {
        String sql = "update video set user_id=?, path=? where id=?";
        jdbcTemplate.update(sql, new Object[]{user_id, path, id});
    }

    public String getVideoPathById(int id){
        String sql = "select path from video where id = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{id},String.class);
    }

    //rowMapper
    public class VideoRowMapper implements RowMapper<Video> {
        public Video mapRow(ResultSet rs, int rowNum) throws SQLException {
            Video video = new Video();
            video.setId(rs.getInt("id"));
            video.setUser_id(rs.getInt("user_id"));
            video.setPath(rs.getString("path"));
            return video;
        }
    }
}
