package com.anchorcms.icloud.service.commservice.impl;

import com.anchorcms.icloud.dao.commservice.PictureChangeDao;
import com.anchorcms.icloud.model.commservice.PictureFistpage;
import com.anchorcms.icloud.service.commservice.PictureChangeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2017/2/10.
 */
@Service
@Transactional
public class PictureChangeServiceImpl implements PictureChangeService {

    public PictureFistpage save(PictureFistpage pictureFistpage) {
        return pictureChangeDao.save(pictureFistpage);
    }

    public List<PictureFistpage> get() {
        return pictureChangeDao.get();
    }

    public int update(String imgPath, Date createDate,String nwebpath, int picId,int flag){
        return pictureChangeDao.update(imgPath, createDate,nwebpath, picId,flag);
    }


    @Resource
    private PictureChangeDao pictureChangeDao;
}
