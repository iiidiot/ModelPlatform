package dao;

import model.Texture;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2017/3/12.
 */
public class TextureDAO {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }
    public JdbcTemplate getJdbcTemplate() { return jdbcTemplate; }

    public int addTexture(int model_id, String path) throws FileNotFoundException {
        String sql = "insert into texture(model_id,path) values(?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                //String sql_sms = "insert into  sms(title,content,date_s,form,sffs,by1,by2,by3) values (?,?,'"+dates+"',?,?,?,?,?)";
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,model_id);
                ps.setString(2, path);
                return ps;
            }
        }, keyHolder);

        int generatedId = keyHolder.getKey().intValue();
        return generatedId;
    }

    public void getTextureById(int texture_id){
        String query = "select * from texture where id = "+texture_id;
        Texture tex = jdbcTemplate.queryForObject(query,  new TextureRowMapper());
        System.out.println(tex.getPath());
    }

    public String getTexturePathById(int id){
        String sql = "select path from texture where id = ?";
        return jdbcTemplate.queryForObject(sql,new Object[]{id},String.class);
    }

    //rowMapper
    public class TextureRowMapper implements RowMapper<Texture> {
        public Texture mapRow(ResultSet rs, int rowNum) throws SQLException {
            Texture tex = new Texture();
            tex.setId(rs.getInt("id"));
            tex.setModel_id(rs.getInt("model_id"));
            tex.setPath(rs.getString("path"));
            return tex;
        }
    }
}
