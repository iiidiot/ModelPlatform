package service;

import dao.ModelDAO;
import dao.SceneDAO;
import dao.TextureDAO;
import model.Scene;
import org.apache.commons.fileupload.FileItem;
import tool.GeneralTool;
import tool.Init;
import tool.JsonTool;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/4/19.
 */
public class SceneService {
    private SceneDAO sceneDAO = (SceneDAO) Init.context.getBean("sceneDAO");

    public String addScene(FileItem scene_data,  int user_id, FileItem thumbnail_data, String name) throws Exception {
        int id = sceneDAO.addScene(user_id, Init.TMP_PATH, name, Init.TMP_PATH);
        //build path in disk for storing the model and texture
        Calendar c = Calendar.getInstance();
        String path1 = user_id+ File.separator+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+ File.separator;
        String path4 = id+File.separator;

        String path = Init.SCENE_STORE_PATH_ROOT+path1+path4 + scene_data.getName();
        String thumbnail_path = Init.SCENE_STORE_PATH_ROOT+path1+path4 + thumbnail_data.getName();
        //create scene file
        //--fail
        if (!GeneralTool.createFile(path)||!GeneralTool.createFile(thumbnail_path))
            return Init.FILE_ERROR;
        //--success create model file
        File file = new File(path);
        scene_data.write(file);
        //--success create model file
        File thumb_file = new File(thumbnail_path);
        thumbnail_data.write(thumb_file);

        sceneDAO.updateScene(id,path,name,thumbnail_path);

        return id+"";
    }

    public String getScenePathById(int id){
        return sceneDAO.getScenePathById(id);
    }

    public String getScenesByUserId(int user_id, int start_page, int page_size) throws IOException {
        int start_row = (start_page-1)*page_size;
        return JsonTool.object2Json(sceneDAO.getScenesByUserId(user_id, start_row, page_size));
    }
}
