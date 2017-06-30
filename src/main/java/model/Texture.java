package model;

import java.io.File;
import java.sql.Blob;

/**
 * Created by Administrator on 2017/3/12.
 */
public class Texture {
    private int id;
    private int model_id;
    private String path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModel_id() {
        return model_id;
    }

    public void setModel_id(int model_id) {
        this.model_id = model_id;
    }

    public String getPath() {  return path;  }

    public void setPath(String path) {  this.path = path;  }
}
