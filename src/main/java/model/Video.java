package model;

/**
 * Created by Administrator on 2017/6/14.
 */
public class Video {
    private int id;
    private int user_id;
    private String path;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getPath() { return path; }

    public void setPath(String path) { this.path = path; }

    public int getUser_id() { return user_id; }

    public void setUser_id(int user_id) { this.user_id = user_id; }
}
