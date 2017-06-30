package dao;

import model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import tool.Init;
import tool.JsonTool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */
public class UserDAO {
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addUser(String username, String password) throws FileNotFoundException {
        String sql = "insert into user(username,password) values(?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {

                //String sql_sms = "insert into  sms(title,content,date_s,form,sffs,by1,by2,by3) values (?,?,'"+dates+"',?,?,?,?,?)";
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, username);
                ps.setString(2, password);
                return ps;
            }
        }, keyHolder);

        int generatedId = keyHolder.getKey().intValue();
        return generatedId;
    }

    public List<User> getUserByID(int uid) throws IOException {
        String sql = "select * from user where id = ?";

        return jdbcTemplate.query(sql,new Object[]{uid},new UserRowMapper());
    }

    public List<User> getUserByUsername(String username) throws IOException {
        String sql = "select * from user where username = ?";

        return jdbcTemplate.query(sql,new Object[]{username},new UserRowMapper());
    }

    public List getAllUsers(int begin, int row_num){
        String sql = "select id,username from user order by id limit ?,?";
        return jdbcTemplate.queryForList(sql,new Object[]{begin, row_num});
    }

    public int countAllUsers(){
        String sql = "select count(*) from user";
        return  jdbcTemplate.queryForInt(sql);
    }

    //rowMapper
    public class UserRowMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
}
