package controller;

import service.UserService;
import tool.GeneralTool;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.io.IOException;

/**
 * Created by Administrator on 2017/3/27.
 */
@Path("/user")
public class UserController {
    private UserService userService = new UserService();

    @GET
    public String testConnection(@Context HttpServletRequest request) {
        //System.out.println("connection ok" +" from "+ GeneralTool.getIpAddr(request));
        return "connection ok";
    }

    @POST
    @Path("/register")
    public String register(@FormParam("username") String username,
                           @FormParam("password") String password,
                           @Context HttpServletRequest request) throws IOException {
        //System.out.println("login:"+username+" & "+password +" from "+ GeneralTool.getIpAddr(request));
        return userService.register(username,password, GeneralTool.getIpAddr(request));
    }

    @POST
    @Path("/login")
    public String login(@FormParam("username") String username,
                        @FormParam("password") String password,
                        @Context HttpServletRequest request) throws IOException {
        //System.out.println("login:"+username+" & "+password +" from "+ GeneralTool.getIpAddr(request));
        return userService.login(username,password, GeneralTool.getIpAddr(request));
    }

    @GET
    @Path("/getAllUsers")
    public String getAllUsers(@QueryParam("startRow") int startRow,
                              @QueryParam("pageSize") int pageSize,
                              @Context HttpServletRequest request) throws IOException {
        //System.out.println("connection ok" +" from "+ GeneralTool.getIpAddr(request));
        return userService.getAllUsers(startRow,pageSize);
    }

    @GET
    @Path("/countAllUsers")
    public int countAllUsers(@Context HttpServletRequest request) throws IOException {
        //System.out.println("connection ok" +" from "+ GeneralTool.getIpAddr(request));
        return userService.countAllUsers();
    }

    @POST
    @Path("/admin")
    public int adminLogin(@FormParam("adminName") String adminName,
                        @FormParam("password") String password,
                        @Context HttpServletRequest request) throws IOException {
        //System.out.println("login:"+username+" & "+password +" from "+ GeneralTool.getIpAddr(request));
        if ("root".equals(adminName)&&"yxb@5216".equals(password))
            return 1;
        else
            return 0;
    }
}
