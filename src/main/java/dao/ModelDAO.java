package dao;

import model.Model;
import model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import tool.Init;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */
public class ModelDAO {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }
    public JdbcTemplate getJdbcTemplate() { return jdbcTemplate; }

    public int addModel(int texture_id, String path, int user_id, String thumbnail_path, String name) throws FileNotFoundException {
        String sql = "insert into model(texture_id,path,user_id, thumbnail_path, name, skin_path) values(?,?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,texture_id);
                ps.setString(2, path);
                ps.setInt(3,user_id);
                ps.setString(4,thumbnail_path);
                ps.setString(5,name);
                ps.setString(6, Init.TMP_PATH);
                return ps;
            }
        }, keyHolder);

        int generatedId = keyHolder.getKey().intValue();
        return generatedId;
    }

    public void updateModel(int model_id, int texture_id, String path, String thumbnail_path, String name) throws FileNotFoundException {
        String sql = "update model set texture_id=?, path=?, thumbnail_path=?, name=? where id=?";
        jdbcTemplate.update(sql, new Object[]{texture_id, path, thumbnail_path, name, model_id});
    }

    public void updateModelSkin(int model_id, String skin_path) throws FileNotFoundException {
        String sql = "update model set skin_path=? where id=?";
        jdbcTemplate.update(sql, new Object[]{skin_path, model_id});
    }

    public List<Model> getModelById(int id){
        String sql = "select * from model where id = ?";
        return jdbcTemplate.query(sql,new Object[]{id},new ModelRowMapper());
    }

    public List<Model> getModelByIdAndUserId(int id,int user_id){
        String sql = "select * from model where id = ? and user_id=?";
        return jdbcTemplate.query(sql,new Object[]{id,user_id},new ModelRowMapper());
    }

    public String getModelPathById(int id){
        String sql = "select path from model where id = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{id},String.class);
    }

    public String getModelPathByIdAndUserId(int id, int user_id){
        String sql = "select path from model where id = ? and user_id=?";
        return jdbcTemplate.queryForObject(sql,new Object[]{id,user_id},String.class);
    }

    public List getModelsByUserId(int user_id, int begin, int row_num){
        String sql = "select id, texture_id, path, thumbnail_path, name, skin_path from model where user_id=? limit ?,?";
        return jdbcTemplate.queryForList(sql,new Object[]{user_id, begin, row_num});
    }

    public int getModelNumByUserId(int user_id){
        String sql = "select count(*) from model where user_id = "+user_id;
        return  jdbcTemplate.queryForInt(sql);
    }

    public void deleteModelById(int id){
        String sql = "delete from model where id = "+id;
        jdbcTemplate.update(sql);
    }

    public int countAllModels(){
        String sql = "select count(*) from model";
        return  jdbcTemplate.queryForInt(sql);
    }

    //rowMapper
    public class ModelRowMapper implements RowMapper<Model> {
        public Model mapRow(ResultSet rs, int rowNum) throws SQLException {
            Model model = new Model();
            model.setId(rs.getInt("id"));
            model.setTexture_id(rs.getInt("texture_id"));
            model.setPath(rs.getString("path"));
            model.setUser_id(rs.getInt("user_id"));
            model.setThumbnail_path(rs.getString("thumbnail_path"));
            model.setName(rs.getString("name"));
            model.setSkin_path(rs.getString("skin_path"));
            return model;
        }
    }

}
