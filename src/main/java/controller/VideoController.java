package controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import service.VideoService;
import tool.Init;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */
@Path("/video")
public class VideoController {
    private VideoService videoService = new VideoService();

    @GET
    @Path("/path")
    public String getVideoPathById(@QueryParam("id") int id, @Context HttpServletRequest request) {
        //System.out.println("get model path by id:"+ id +" from "+ GeneralTool.getIpAddr(request));
        String path = videoService.getVideoPathById(id);
        return path;
    }

    @POST
    @Path("/upload")
    public String skinUpload(@Context HttpServletRequest request) throws Exception {
        int user_id = 0;
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        //设置上传文件大小的上限，-1表示无上限
        fileUpload.setFileSizeMax(Init.MAX_VIDEO_SIZE);//单个文件最大5M
        fileUpload.setSizeMax(Init.MAX_FORM_SIZE);//整个表单最大5M
        fileUpload.setHeaderEncoding("utf-8");//支持中文

        List items  = fileUpload.parseRequest(request);
        if (items.size()!=2){
            return Init.FORMAT_ERROR;
        }
        //System.out.println("model upload from "+ GeneralTool.getIpAddr(request)+" file num:"+items.size());
        //获取内容然后存储
        Iterator iterator = items.iterator();
        FileItem videoData=null;
        while(iterator.hasNext()){
            FileItem fileItem =(FileItem)iterator.next();
            if ("user_id".equals(fileItem.getFieldName())){
                user_id = Integer.parseInt(fileItem.getString());
            }
            else if ("video".equals(fileItem.getFieldName())){
                videoData = fileItem;
            }
        }
        String result = Init.FORMAT_ERROR;
        if (videoData!=null && user_id!=0) {
            result = videoService.addVideo(user_id, videoData);
        }
        return result;
    }
}
