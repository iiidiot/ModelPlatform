package service;


import dao.InfoDAO;
import dao.ModelDAO;
import dao.TextureDAO;
import dao.UserDAO;
import model.Model;
import org.apache.commons.fileupload.FileItem;
import tool.GeneralTool;
import tool.Init;
import tool.JsonTool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */
public class ModelService {
    private ModelDAO modelDAO = (ModelDAO) Init.context.getBean("modelDAO");
    private TextureDAO textureDAO = (TextureDAO) Init.context.getBean("textureDAO");
    private InfoDAO infoDAO = (InfoDAO) Init.context.getBean("infoDAO");

    public String addModel(FileItem model_data, FileItem texture_data, int user_id, FileItem thumbnail_data, String name) throws Exception {
        int model_num = modelDAO.getModelNumByUserId(user_id);
        if(model_num>=Init.MAX_MODEL_PER_USER){
            return Init.MAX_LIMIT;
        }

        int model_id = modelDAO.addModel(Init.TMP_ID, Init.TMP_PATH, user_id, Init.TMP_PATH, name);
        //build path in disk for storing the model and texture
        Calendar c = Calendar.getInstance();
        String path1 = user_id+File.separator+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+File.separator;
        String path4 = model_id+File.separator;

        String model_path = Init.MODEL_STORE_PATH_ROOT+path1+path4 + model_data.getName();
        String texture_path = Init.MODEL_STORE_PATH_ROOT+path1+path4 + texture_data.getName();
        String thumbnail_path = Init.MODEL_STORE_PATH_ROOT+path1+path4 + thumbnail_data.getName();
        //create model & texture file
        //--fail
        if (!GeneralTool.createFile(model_path) || !GeneralTool.createFile(texture_path) || !GeneralTool.createFile(thumbnail_path))
            return Init.FILE_ERROR;
        //--success create model file
        File mfile = new File(model_path);
        model_data.write(mfile);
        //--success create texture file
        File tfile = new File(texture_path);
        texture_data.write(tfile);
        //--success create thumbnail file
        File thumbfile = new File(thumbnail_path);
        thumbnail_data.write(thumbfile);

        //update table in db
        int texture_id = textureDAO.addTexture(model_id,texture_path);
        modelDAO.updateModel(model_id,texture_id,model_path,thumbnail_path,name);

        return model_id+"";
    }

    public String updateModelSkin(int user_id,int model_id,FileItem skinData) throws Exception {
        //build path in disk for storing the model and texture
        String path =modelDAO.getModelPathByIdAndUserId(model_id,user_id);
        //System.out.println("path: " + path);
        path = path.substring(0,path.lastIndexOf(File.separator)+1);
        path = path+skinData.getName();
        //create skin file
        //--fail
        if (!GeneralTool.createFile(path))
            return Init.FILE_ERROR;
        //--success create model file
        File file = new File(path);
        skinData.write(file);

        //add database record
        modelDAO.updateModelSkin(model_id,path);

        return Init.SUCCESS;
    }

    public String getModelPathById(int id){
//        List<Model> models = modelDAO.getModelById(id);
//
//        if (models.isEmpty())
//            return Init.NULL;
//        else
//            return models.get(0).getPath();
        return  modelDAO.getModelPathById(id);
    }

    public String getModelsByUserId(int user_id, int start_page, int page_size) throws IOException {
        int start_row = (start_page-1)*page_size;
        return JsonTool.object2Json(modelDAO.getModelsByUserId(user_id, start_row, page_size));
    }

    public String deleteModelById(int id, int user_id){
        List<Model> models = modelDAO.getModelByIdAndUserId(id,user_id);
        if (models.isEmpty())
            return Init.NULL;

        //删除数据库记录
        String path = models.get(0).getPath();
        modelDAO.deleteModelById(id);
        models = modelDAO.getModelById(id);
        if (models.isEmpty()) {
            //删除文件
            GeneralTool.deleteFile(path);
            return Init.SUCCESS;
        }
        else
            return Init.ERROR;
    }

    public int getModelNumByUserId(int user_id) throws IOException {
        return modelDAO.getModelNumByUserId(user_id);
    }

    public int countAllModels(){
        return modelDAO.countAllModels();
    }

    public void recordUploadModel(int user_id, String ip){
        infoDAO.addUploadModelInfo(user_id,ip,System.currentTimeMillis());
    }
    public void recordDownloadModel(int user_id, String ip){
        infoDAO.addDownloadModelInfo(user_id,ip,System.currentTimeMillis());
    }
    public void recordDeleteModel(int user_id, String ip){
        infoDAO.addDeleteModelInfo(user_id,ip,System.currentTimeMillis());
    }
}
