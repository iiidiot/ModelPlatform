import dao.InfoDAO;
import model.User;
import service.ModelService;
import service.TextureService;
import tool.GeneralTool;
import tool.Init;
import dao.UserDAO;
import service.UserService;

import java.io.IOException;
import java.io.SyncFailedException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/12.
 */
public class Test {
//    private DataSource dataSource;
//    private JdbcTemplate jdbcTemplate;
//    public void setDataSource(DataSource ds) {
//        this.dataSource = ds;
//        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
//    }
    public static void main(String[] args) throws IOException {
        new Init();
        UserService userService = new UserService();
        ModelService modelService = new ModelService();
        TextureService textureService = new TextureService();

        InfoDAO infoDAO = (InfoDAO) Init.context.getBean("infoDAO");
        //infoDAO.addLoginInfo(1,"202.120.95.230", System.currentTimeMillis());
        //infoDAO.addRegisterInfo(1,"202.120.95.230", System.currentTimeMillis());
        //infoDAO.addUploadModelInfo(1,"202.120.95.230", System.currentTimeMillis());
        //infoDAO.addDownloadModelInfo(1,"202.120.95.230", System.currentTimeMillis());
        //infoDAO.addDeleteModelInfo(1,"202.120.95.230", System.currentTimeMillis());
        GeneralTool generalTool = new GeneralTool();
        String ip = "218.81.19.194";
        //String address = generalTool.getAddresses("ip="+ip, "utf-8");

        //String p = userService.getAllUsers(0,5);
        //String p = modelService.getModelsByUserId(2,1,50);
        //int p = infoDAO.countAllDeleteModelInfoDuringTime(0,System.currentTimeMillis());
        System.out.println(GeneralTool.deleteFile("C:/mnt/models/2/2017-5-18/30/0.obj"));
        System.out.println("test finished");
    }
}
