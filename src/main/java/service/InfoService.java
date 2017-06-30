package service;

import dao.InfoDAO;
import tool.Init;
import tool.JsonTool;

import java.io.IOException;

/**
 * Created by Administrator on 2017/6/20.
 */
public class InfoService {
    private InfoDAO infoDAO = (InfoDAO) Init.context.getBean("infoDAO");

    //count all
    public int countAllLoginDuringTime(long start_time, long end_time) throws IOException {
        return infoDAO.countAllLoginDuringTime(start_time,end_time);
    }
    public int countAllRegisterDuringTime(long start_time, long end_time) throws IOException {
        return infoDAO.countAllRegisterDuringTime(start_time,end_time);
    }
    public int countAllUploadModelDuringTime(long start_time, long end_time) throws IOException {
        return infoDAO.countAllUploadModelDuringTime(start_time,end_time);
    }
    public int countAllDownloadModelDuringTime(long start_time, long end_time) throws IOException {
        return infoDAO.countAllDownloadModelDuringTime(start_time,end_time);
    }
    public int countAllDeleteModelDuringTime(long start_time, long end_time) throws IOException {
        return infoDAO.countAllDeleteModelDuringTime(start_time,end_time);
    }

    //by user
    public int countLoginByUserIdDuringTime(int user_id, long start_time, long end_time) throws IOException {
        return infoDAO.countLoginByUserIdDuringTime(user_id, start_time,end_time);
    }
    public int countRegisterByUserIdDuringTime(int user_id, long start_time, long end_time) throws IOException {
        return infoDAO.countRegisterByUserIdDuringTime(user_id, start_time,end_time);
    }
    public int countUploadModelByUserIdDuringTime(int user_id, long start_time, long end_time) throws IOException {
        return infoDAO.countUploadModelByUserIdDuringTime(user_id, start_time,end_time);
    }
    public int countDownloadModelByUserIdDuringTime(int user_id, long start_time, long end_time) throws IOException {
        return infoDAO.countDownloadModelByUserIdDuringTime(user_id, start_time,end_time);
    }
    public int countDeleteModelByUserIdDuringTime(int user_id, long start_time, long end_time) throws IOException {
        return infoDAO.countDeleteModelByUserIdDuringTime(user_id, start_time,end_time);
    }

    //ip
    public String lastLoginIpByUserId(int user_id) throws IOException {
        return infoDAO.lastLoginIpByUserId(user_id);
    }
}
