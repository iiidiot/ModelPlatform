package service;

import dao.VideoDAO;
import org.apache.commons.fileupload.FileItem;
import tool.GeneralTool;
import tool.Init;

import java.io.File;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/6/14.
 */
public class VideoService {
    private VideoDAO videoDAO = (VideoDAO) Init.context.getBean("videoDAO");

    public String addVideo(int user_id, FileItem video_data) throws Exception {
        int video_id = videoDAO.addVideo(user_id, Init.TMP_PATH);
        //build path in disk for storing video
        Calendar c = Calendar.getInstance();
        String path1 = user_id+ File.separator+c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DATE)+File.separator;
        String path4 = video_id+File.separator;

        String video_path = Init.VIDEO_STORE_PATH_ROOT+path1+path4 + video_data.getName();
        //create video file
        //--fail
        if (!GeneralTool.createFile(video_path))
            return Init.FILE_ERROR;
        //--success create video file
        File f = new File(video_path);
        video_data.write(f);

        //update table in db
        videoDAO.updateVideo(video_id,user_id,video_path);

        return video_id+"";
    }

    public String getVideoPathById(int id){
        return  videoDAO.getVideoPathById(id);
    }
}
