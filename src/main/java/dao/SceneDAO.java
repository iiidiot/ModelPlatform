package dao;

import model.Model;
import model.Scene;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */
public class SceneDAO {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }
    public JdbcTemplate getJdbcTemplate() { return jdbcTemplate; }

    public int addScene(int user_id, String path, String name, String thumbnail_path) throws FileNotFoundException {
        String sql = "insert into scene(user_id,path,name,thumbnail_path) values(?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,user_id);
                ps.setString(2, path);
                ps.setString(3, name);
                ps.setString(4, thumbnail_path);
                return ps;
            }
        }, keyHolder);

        int generatedId = keyHolder.getKey().intValue();
        return generatedId;
    }

    public void updateScene(int id, String path, String name, String thumbnail_path) throws FileNotFoundException {
        String sql = "update scene set path=?,name=?,thumbnail_path=? where id=?";
        jdbcTemplate.update(sql, new Object[]{path, name, thumbnail_path, id});
    }

    public List<Scene> getSceneById(int id){
        String sql = "select * from scene where id = ?";
        return jdbcTemplate.query(sql,new Object[]{id},new SceneRowMapper());
    }

    public String getScenePathById(int id){
        String sql = "select path from scene where id = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{id},String.class);
    }

    public List getScenesByUserId(int user_id, int begin, int row_num){
        String sql = "select id, path, name, thumbnail_path from scene where user_id=? limit ?,?";
        return jdbcTemplate.queryForList(sql,new Object[]{user_id, begin, row_num});
    }

    //rowMapper
    public class SceneRowMapper implements RowMapper<Scene> {
        public Scene mapRow(ResultSet rs, int rowNum) throws SQLException {
            Scene scene = new Scene();
            scene.setId(rs.getInt("id"));
            scene.setPath(rs.getString("path"));
            scene.setUser_id(rs.getInt("user_id"));
            scene.setName(rs.getString("name"));
            scene.setThumbnail_path(rs.getString("thumbnail_path"));
            return scene;
        }
    }
}
