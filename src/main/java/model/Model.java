package model;

/**
 * Created by Administrator on 2017/4/10.
 */
public class Model {
    private int id;
    private int user_id;
    private int texture_id;
    private String path;
    private String thumbnail_path;
    private String name;
    private String skin_path;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getTexture_id() { return texture_id; }

    public void setTexture_id(int texture_id) { this.texture_id = texture_id; }

    public String getPath() { return path; }

    public void setPath(String path) { this.path = path; }

    public int getUser_id() { return user_id; }

    public void setUser_id(int user_id) { this.user_id = user_id; }

    public String getThumbnail_path() { return thumbnail_path; }

    public void setThumbnail_path(String thumbnail_path) { this.thumbnail_path = thumbnail_path; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSkin_path() { return skin_path; }

    public void setSkin_path(String skin_path) { this.skin_path = skin_path; }
}
