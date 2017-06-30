package controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.ModelService;
import service.TextureService;
import tool.GeneralTool;
import tool.Init;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 */
@Path("/model")
public class ModelController {
    private ModelService modelService = new ModelService();
    private TextureService textureService = new TextureService();

    @GET
    public String testConnection(@Context HttpServletRequest request) {
        //System.out.println("connection ok" +" from "+ GeneralTool.getIpAddr(request));
        return "connection ok";
    }

    @POST
    @Path("/upload")
    public String upload(@Context HttpServletRequest request) throws Exception {
        int user_id = 0;
        String name = "";
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        //设置上传文件大小的上限，-1表示无上限
        fileUpload.setFileSizeMax(Init.MAX_FILE_SIZE);//单个文件最大2M
        fileUpload.setSizeMax(Init.MAX_FORM_SIZE);//整个表单最大5M
        fileUpload.setHeaderEncoding("utf-8");//支持中文

        List items  = fileUpload.parseRequest(request);
        if (items.size()!=5){
            return Init.FORMAT_ERROR;
        }
        //System.out.println("model upload from "+ GeneralTool.getIpAddr(request)+" file num:"+items.size());
        //获取内容然后存储
        Iterator iterator = items.iterator();
        FileItem modelData=null, textureData=null, thumbnailData=null;
        while(iterator.hasNext()){
            FileItem fileItem =(FileItem)iterator.next();
            if ("model".equals(fileItem.getFieldName())){
                modelData = fileItem;
            }
            else if ("texture".equals(fileItem.getFieldName())){
                textureData = fileItem;
            }
            else if ("user_id".equals(fileItem.getFieldName())){
                user_id = Integer.parseInt(fileItem.getString());
            }
            else if ("name".equals(fileItem.getFieldName())){
                name = new String(fileItem.get(), "utf-8");//支持中文
            }
            else if ("thumbnail".equals(fileItem.getFieldName())){
                thumbnailData = fileItem;
            }
        }
        String model_id = Init.FORMAT_ERROR;
        if (modelData!=null && textureData!=null && user_id!=0 && thumbnailData!=null && !"".equals(name)) {
            model_id = modelService.addModel(modelData, textureData, user_id, thumbnailData, name);
            modelService.recordUploadModel(user_id,GeneralTool.getIpAddr(request));
        }
        return model_id;
    }

    @GET
    @Path("/path")
    public String getModelPathById(@QueryParam("id") int id, @Context HttpServletRequest request) {
        //System.out.println("get model path by id:"+ id +" from "+ GeneralTool.getIpAddr(request));
        String path = modelService.getModelPathById(id);
        //modelService.recordDownloadModel(user_id,GeneralTool.getIpAddr(request));
        return path;
    }
    @GET
    @Path("/texture/path")
    public String getTexturePathById(@QueryParam("id") int id, @QueryParam("user_id") int user_id, @Context HttpServletRequest request) {
        //System.out.println("get texture path by id:"+ id +" from "+ GeneralTool.getIpAddr(request));
        String path = textureService.getTexturePathById(id);
        modelService.recordDownloadModel(user_id,GeneralTool.getIpAddr(request));
        return path;
    }

    @GET
    @Path("/getListByUser")
    public String getModelsByUserId(@QueryParam("uid") int uid,
                                       @QueryParam("startPage") int startPage,
                                       @QueryParam("pageSize") int pageSize,
                                       @Context HttpServletRequest request) throws IOException {
        //System.out.println("get model path by id:"+ id +" from "+ GeneralTool.getIpAddr(request));
        String path = modelService.getModelsByUserId(uid,startPage,pageSize);
        return path;
    }

    @POST
    @Path("/delete")
    public String deleteModel(@FormParam("user_id") int user_id,
                        @FormParam("model_id") int model_id,
                        @Context HttpServletRequest request) throws IOException {
        //System.out.println("login:"+username+" & "+password +" from "+ GeneralTool.getIpAddr(request));
        modelService.recordDeleteModel(user_id,GeneralTool.getIpAddr(request));
        return modelService.deleteModelById(model_id,user_id);
    }

    @POST
    @Path("/skinUpload")
    public String skinUpload(@Context HttpServletRequest request) throws Exception {
        int user_id = 0;
        int model_id = 0;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        //设置上传文件大小的上限，-1表示无上限
        fileUpload.setFileSizeMax(Init.MAX_FILE_SIZE);//单个文件最大2M
        fileUpload.setSizeMax(Init.MAX_FORM_SIZE);//整个表单最大5M
        fileUpload.setHeaderEncoding("utf-8");//支持中文

        List items  = fileUpload.parseRequest(request);
        if (items.size()!=3){
            return Init.FORMAT_ERROR;
        }
        //System.out.println("model upload from "+ GeneralTool.getIpAddr(request)+" file num:"+items.size());
        //获取内容然后存储
        Iterator iterator = items.iterator();
        FileItem skinData=null;
        while(iterator.hasNext()){
            FileItem fileItem =(FileItem)iterator.next();
            if ("model_id".equals(fileItem.getFieldName())){
                model_id = Integer.parseInt(fileItem.getString());
            }
            else if ("skin".equals(fileItem.getFieldName())){
                skinData = fileItem;
            }
            else if ("user_id".equals(fileItem.getFieldName())){
                user_id = Integer.parseInt(fileItem.getString());
            }
        }
        String result = Init.FORMAT_ERROR;
        if (skinData!=null && user_id!=0 && model_id!=0) {
            result = modelService.updateModelSkin(user_id,model_id,skinData);
        }
        return result;
    }

    @GET
    @Path("/getModelNumByUserId")
    public int getModelNumByUserId(@QueryParam("id") int id, @Context HttpServletRequest request) throws IOException {
        //System.out.println("get model path by id:"+ id +" from "+ GeneralTool.getIpAddr(request));
        return modelService.getModelNumByUserId(id);
    }

    @GET
    @Path("/countAllModels")
    public int countAllModels( @Context HttpServletRequest request) {
        //System.out.println("get model path by id:"+ id +" from "+ GeneralTool.getIpAddr(request));
        return modelService.countAllModels();
    }
}
