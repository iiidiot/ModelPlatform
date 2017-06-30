package controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.ModelService;
import service.SceneService;
import tool.Init;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 */
@Path("/scene")
public class SceneController {
    private SceneService sceneService = new SceneService();

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
        fileUpload.setHeaderEncoding("utf-8");

        List items  = fileUpload.parseRequest(request);
        if (items.size()!=4){
            System.out.println("no");
            return Init.FORMAT_ERROR;
        }
        //System.out.println("model upload from "+ GeneralTool.getIpAddr(request)+" file num:"+items.size());
        //获取内容然后存储
        Iterator iterator = items.iterator();
        FileItem scene_data=null, thumbnail_data=null;
        while(iterator.hasNext()){
            FileItem fileItem =(FileItem)iterator.next();
            if ("scene".equals(fileItem.getFieldName())) {
                System.out.println("scene");
                scene_data = fileItem;
            }
            else if ("user_id".equals(fileItem.getFieldName())){
                System.out.println("user_id");
                user_id = Integer.parseInt(fileItem.getString());
            }
            else if ("thumbnail".equals(fileItem.getFieldName())){
                System.out.println("thumbnail");
                thumbnail_data = fileItem;
            }
            else if ("name".equals(fileItem.getFieldName())){
                System.out.println("name");
                name = new String(fileItem.getString().getBytes("ISO8859_1"), "utf-8");//支持中文
            }
        }
        String scene_id = Init.FORMAT_ERROR;
        if (scene_data!=null && user_id!=0 && !"".equals(name) && thumbnail_data!=null) {
            System.out.println("yes");
            scene_id = sceneService.addScene(scene_data, user_id,thumbnail_data,name);
        }
        return scene_id;
    }

    @GET
    @Path("/path")
    public String getScenePath(@QueryParam("id") int id, @Context HttpServletRequest request) {
        //System.out.println("get scene path by id:"+ id +" from "+ GeneralTool.getIpAddr(request));
        String path = sceneService.getScenePathById(id);
        return path;
    }

    @GET
    @Path("/getListByUser")
    public String getScenesByUserId(@QueryParam("uid") int uid,
                                    @QueryParam("startPage") int startPage,
                                    @QueryParam("pageSize") int pageSize,
                                    @Context HttpServletRequest request) throws IOException {
        //System.out.println("get scene list by uid:"+ uid +" from "+ GeneralTool.getIpAddr(request));
        String path = sceneService.getScenesByUserId(uid,startPage,pageSize);
        return path;
    }
}
