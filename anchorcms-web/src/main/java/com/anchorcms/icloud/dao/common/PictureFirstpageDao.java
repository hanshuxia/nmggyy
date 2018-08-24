package com.anchorcms.icloud.dao.common;

import com.anchorcms.icloud.model.commservice.PictureFistpage;

import java.util.List;

/**
 * Created by ${耿文龙} on 2017/2/10.
 */
public interface PictureFirstpageDao {
    /**
     * @author gengwenlong
     * @Date 2017/2/10 14:00
     * @return
     * 自定义标签-图片轮播
     */
    public List<PictureFistpage> getPictureFistpageList(Integer count, Integer orderBy, Integer listType);
}
