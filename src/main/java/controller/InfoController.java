package controller;

import service.InfoService;
import tool.GeneralTool;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/20.
 */
@Path("/info")
public class InfoController {
    private InfoService infoService = new InfoService();

    //count all
    @POST
    @Path("/countAllLoginDuringTime")
    public int countAllLoginDuringTime(@FormParam("start_time") long start_time,
                        @FormParam("end_time") long end_time,
                        @Context HttpServletRequest request) throws IOException {
        return infoService.countAllLoginDuringTime(start_time,end_time);
    }
    @POST
    @Path("/countAllRegisterDuringTime")
    public int countAllRegisterDuringTime(@FormParam("start_time") long start_time,
                                       @FormParam("end_time") long end_time,
                                       @Context HttpServletRequest request) throws IOException {
        return infoService.countAllRegisterDuringTime(start_time,end_time);
    }
    @POST
    @Path("/countAllUploadModelDuringTime")
    public int countAllUploadModelDuringTime(@FormParam("start_time") long start_time,
                                          @FormParam("end_time") long end_time,
                                          @Context HttpServletRequest request) throws IOException {
        return infoService.countAllUploadModelDuringTime(start_time,end_time);
    }
    @POST
    @Path("/countAllDownloadModelDuringTime")
    public int countAllDownloadModelDuringTime(@FormParam("start_time") long start_time,
                                          @FormParam("end_time") long end_time,
                                          @Context HttpServletRequest request) throws IOException {
        return infoService.countAllDownloadModelDuringTime(start_time,end_time);
    }
    @POST
    @Path("/countAllDeleteModelDuringTime")
    public int countAllDeleteModelDuringTime(@FormParam("start_time") long start_time,
                                          @FormParam("end_time") long end_time,
                                          @Context HttpServletRequest request) throws IOException {
        return infoService.countAllDeleteModelDuringTime(start_time,end_time);
    }

    //by user
    @POST
    @Path("/countLoginByUserIdDuringTime")
    public int countLoginByUserIdDuringTime(@FormParam("user_id") int user_id,
                                             @FormParam("start_time") long start_time,
                                             @FormParam("end_time") long end_time,
                                             @Context HttpServletRequest request) throws IOException {
        return infoService.countLoginByUserIdDuringTime(user_id,start_time,end_time);
    }
    @POST
    @Path("/countRegisterByUserIdDuringTime")
    public int countRegisterByUserIdDuringTime(@FormParam("user_id") int user_id,
                                            @FormParam("start_time") long start_time,
                                            @FormParam("end_time") long end_time,
                                            @Context HttpServletRequest request) throws IOException {
        return infoService.countRegisterByUserIdDuringTime(user_id,start_time,end_time);
    }
    @POST
    @Path("/countUploadModelByUserIdDuringTime")
    public int countUploadModelByUserIdDuringTime(@FormParam("user_id") int user_id,
                                            @FormParam("start_time") long start_time,
                                            @FormParam("end_time") long end_time,
                                            @Context HttpServletRequest request) throws IOException {
        return infoService.countUploadModelByUserIdDuringTime(user_id,start_time,end_time);
    }
    @POST
    @Path("/countDownloadModelByUserIdDuringTime")
    public int countDownloadModelByUserIdDuringTime(@FormParam("user_id") int user_id,
                                            @FormParam("start_time") long start_time,
                                            @FormParam("end_time") long end_time,
                                            @Context HttpServletRequest request) throws IOException {
        return infoService.countDownloadModelByUserIdDuringTime(user_id,start_time,end_time);
    }
    @POST
    @Path("/countDeleteModelByUserIdDuringTime")
    public int countDeleteModelByUserIdDuringTime(@FormParam("user_id") int user_id,
                                            @FormParam("start_time") long start_time,
                                            @FormParam("end_time") long end_time,
                                            @Context HttpServletRequest request) throws IOException {
        return infoService.countDeleteModelByUserIdDuringTime(user_id,start_time,end_time);
    }

    //last ip
    @POST
    @Path("/lastLoginIpByUserId")
    public String lastLoginIpByUserId(@FormParam("user_id") int user_id,
                                      @Context HttpServletRequest request) throws IOException {
        GeneralTool generalTool = new GeneralTool();
        String ip = infoService.lastLoginIpByUserId(user_id);
        String address = generalTool.getAddresses("ip="+ip, "utf-8");
        return address+" "+ip;
    }
}
