package service;

import dao.InfoDAO;
import model.User;
import tool.GeneralTool;
import tool.Init;
import dao.UserDAO;
import tool.JsonTool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */
public class UserService {
    private UserDAO userDAO = (UserDAO) Init.context.getBean("userDAO");
    private InfoDAO infoDAO = (InfoDAO) Init.context.getBean("infoDAO");

    public String register(String username, String password, String ip) throws IOException {
        List<User> users = userDAO.getUserByUsername(username);
        if (users.isEmpty()) {
            int uid = userDAO.addUser(username, password);
            infoDAO.addRegisterInfo(uid,ip,System.currentTimeMillis());
            return uid+"";
        }
        else {
            return Init.DUPLICATE;
        }
    }

    public String login(String username, String password, String ip) throws IOException {
        List<User> users  = userDAO.getUserByUsername(username);
        if (users.isEmpty()){
            return Init.NULL;
        }
        else {
            User user = users.get(0);
            if (user.getPassword().equals(password)){
                infoDAO.addLoginInfo(user.getId(),ip,System.currentTimeMillis());
                return user.getId()+"";
            }
            else {
                return Init.WRONG_PASSWORD;
            }
        }
    }

    public String getAllUsers(int begin, int page_size) throws IOException {
        String result = JsonTool.object2Json(userDAO.getAllUsers(begin,page_size));
        return result;
    }

    public int countAllUsers() throws IOException {
        return userDAO.countAllUsers();
    }
}
