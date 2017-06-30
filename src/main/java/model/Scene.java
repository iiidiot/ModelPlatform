package model;

/**
 * Created by Administrator on 2017/4/19.
 */
public class Scene {
    private int id;
    private int user_id;
    private String path;
    private String name;
    private String thumbnail_path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPath() {  return path;  }

    public void setPath(String path) {  this.path = path;  }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getThumbnail_path() { return thumbnail_path; }

    public void setThumbnail_path(String thumbnail_path) { this.thumbnail_path = thumbnail_path; }
}
