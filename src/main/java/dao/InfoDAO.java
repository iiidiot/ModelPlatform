package dao;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by Administrator on 2017/6/18.
 */
public class InfoDAO {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) { this.jdbcTemplate = jdbcTemplate; }
    public JdbcTemplate getJdbcTemplate() { return jdbcTemplate; }

    //add
    public void addLoginInfo(int user_id, String ip, long time){
        String sql = "insert into LoginInfo(user_id, ip, time) values(?,?,?)";
        jdbcTemplate.update(sql, new Object[]{user_id, ip, time});
    }
    public void addRegisterInfo(int user_id, String ip, long time){
        String sql = "insert into RegisterInfo(user_id, ip, time) values(?,?,?)";
        jdbcTemplate.update(sql, new Object[]{user_id, ip, time});
    }
    public void addUploadModelInfo(int user_id, String ip, long time){
        String sql = "insert into UploadModelInfo(user_id, ip, time) values(?,?,?)";
        jdbcTemplate.update(sql, new Object[]{user_id, ip, time});
    }
    public void addDeleteModelInfo(int user_id, String ip, long time){
        String sql = "insert into DeleteModelInfo(user_id, ip, time) values(?,?,?)";
        jdbcTemplate.update(sql, new Object[]{user_id, ip, time});
    }
    public void addDownloadModelInfo(int user_id, String ip, long time){
        String sql = "insert into DownloadModelInfo(user_id, ip, time) values(?,?,?)";
        jdbcTemplate.update(sql, new Object[]{user_id, ip, time});
    }

    //last ip
    public String lastLoginIpByUserId(int user_id){
        String sql = "select ip from LoginInfo where user_id = " + user_id +" order by id desc limit 1 ";
        return  jdbcTemplate.queryForObject(sql, String.class);
    }
    public String lastRegisterIpByUserId(int user_id){
        String sql = "select ip from RegisterInfo where user_id = " + user_id +" order by id desc limit 1 ";
        return  jdbcTemplate.queryForObject(sql, String.class);
    }
    public String lastUploadModelIpByUserId(int user_id){
        String sql = "select ip from UploadModelInfo where user_id = " + user_id +" order by id desc limit 1 ";
        return  jdbcTemplate.queryForObject(sql, String.class);
    }
    public String lastDownloadModelIpByUserId(int user_id){
        String sql = "select ip from DownloadModelInfo where user_id = " + user_id +" order by id desc limit 1 ";
        return  jdbcTemplate.queryForObject(sql, String.class);
    }
    public String lastDeleteModelInfoIpByUserId(int user_id){
        String sql = "select ip from DeleteModelInfo where user_id = " + user_id +" order by id desc limit 1 ";
        return  jdbcTemplate.queryForObject(sql, String.class);
    }

    /*
    by user
    [start_time, end_time]
     */
    public int countLoginByUserIdDuringTime(int user_id, long start_time, long end_time){
        String sql = "select count(*) from LoginInfo where user_id = ? and time >= ? and time <= ?";
        return  jdbcTemplate.queryForObject(sql, new Object[]{user_id, start_time, end_time}, Integer.class);
    }
    public int countRegisterByUserIdDuringTime(int user_id, long start_time, long end_time){
        String sql = "select count(*) from RegisterInfo where user_id = ? and time >= ? and time <= ?";
        return  jdbcTemplate.queryForObject(sql, new Object[]{user_id, start_time, end_time}, Integer.class);
    }
    public int countUploadModelByUserIdDuringTime(int user_id, long start_time, long end_time){
        String sql = "select count(*) from UploadModelInfo where user_id = ? and time >= ? and time <= ?";
        return  jdbcTemplate.queryForObject(sql, new Object[]{user_id, start_time, end_time}, Integer.class);
    }
    public int countDownloadModelByUserIdDuringTime(int user_id, long start_time, long end_time){
        String sql = "select count(*) from DownloadModelInfo where user_id = ? and time >= ? and time <= ?";
        return  jdbcTemplate.queryForObject(sql, new Object[]{user_id, start_time, end_time}, Integer.class);
    }
    public int countDeleteModelByUserIdDuringTime(int user_id, long start_time, long end_time){
        String sql = "select count(*) from DeleteModelInfo where user_id = ? and time >= ? and time <= ?";
        return  jdbcTemplate.queryForObject(sql, new Object[]{user_id, start_time, end_time}, Integer.class);
    }

    //count all
    public int countAllLoginDuringTime(long start_time, long end_time){
        String sql = "select count(*) from LoginInfo where time >= ? and time <= ?";
        return  jdbcTemplate.queryForObject(sql, new Object[]{start_time, end_time}, Integer.class);
    }
    public int countAllRegisterDuringTime(long start_time, long end_time){
        String sql = "select count(*) from RegisterInfo where time >= ? and time <= ?";
        return  jdbcTemplate.queryForObject(sql, new Object[]{start_time, end_time}, Integer.class);
    }
    public int countAllUploadModelDuringTime(long start_time, long end_time){
        String sql = "select count(*) from UploadModelInfo where time >= ? and time <= ?";
        return  jdbcTemplate.queryForObject(sql, new Object[]{start_time, end_time}, Integer.class);
    }
    public int countAllDownloadModelDuringTime(long start_time, long end_time){
        String sql = "select count(*) from DownloadModelInfo where time >= ? and time <= ?";
        return  jdbcTemplate.queryForObject(sql, new Object[]{start_time, end_time}, Integer.class);
    }
    public int countAllDeleteModelDuringTime(long start_time, long end_time){
        String sql = "select count(*) from DeleteModelInfo where time >= ? and time <= ?";
        return  jdbcTemplate.queryForObject(sql, new Object[]{start_time, end_time}, Integer.class);
    }
}
