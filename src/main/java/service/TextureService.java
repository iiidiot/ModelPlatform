package service;

import dao.TextureDAO;
import tool.Init;

/**
 * Created by Administrator on 2017/3/12.
 */
public class TextureService {
    private TextureDAO dao= (TextureDAO) Init.context.getBean("textureDAO");

    public TextureDAO getDao() {
        return dao;
    }

    public void setDao(TextureDAO dao) {
        this.dao = dao;
    }

    public String getTexturePathById(int id){
        return  dao.getTexturePathById(id);
    }
}
