package tool;

import dao.TextureDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 * Created by Administrator on 2017/3/22.
 */
public final class Init implements ServletContextListener {

    public static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    public static final String NULL = "null";
    public static final String DUPLICATE = "duplicate";
    public static final String SUCCESS = "success";
    public static final String WRONG_PASSWORD = "wrong_password";
    public static final String ERROR = "error";
    public static final String FILE_ERROR = "file error";
    public static final String FORMAT_ERROR = "format error";
    public static final String MAX_LIMIT = "max limit";

    public static final int TMP_ID = 0;
    public static final String TMP_PATH = "0";
    public static final String MODEL_STORE_PATH_ROOT = "/mnt"+ File.separator+"models"+File.separator;
    public static final String SCENE_STORE_PATH_ROOT = "/mnt"+ File.separator+"scenes"+File.separator;
    public static final String VIDEO_STORE_PATH_ROOT = "/mnt"+ File.separator+"videos"+File.separator;

    public static final int MAX_FILE_SIZE = 1024*1024*2;
    public static final int MAX_FORM_SIZE = 1024*1024*5;
    public static final int MAX_VIDEO_SIZE = 1024*1024*5;

    public static final int MAX_MODEL_PER_USER = 10;

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        //Notification that the servlet context is about to be shut down.
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        //Notification that the web application initialization process is starting
    }
}
