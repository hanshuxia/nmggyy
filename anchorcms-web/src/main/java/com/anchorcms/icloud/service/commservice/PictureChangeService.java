package com.anchorcms.icloud.service.commservice;

import com.anchorcms.icloud.model.commservice.PictureFistpage;

import java.util.Date;
import java.util.List;

/**
 * Created by DELL on 2017/2/10.
 */
public interface PictureChangeService {
    public PictureFistpage save(PictureFistpage pictureFistpage);

    public List<PictureFistpage> get();

    public int update(String imgPath, Date createDate,String nwebpath, int picId,int flag);
}
