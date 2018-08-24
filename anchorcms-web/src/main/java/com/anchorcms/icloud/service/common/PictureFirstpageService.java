package com.anchorcms.icloud.service.common;

import com.anchorcms.icloud.model.commservice.PictureFistpage;

import java.util.List;

/**
 * Created by ${耿文龙} on 2017/2/10.
 */
public interface PictureFirstpageService {
    /**
     * @author gengwenlong
     * @Date 2017/2/10 13:54
     * @return
     * 自定义标签-图片轮播
     */
    public List<PictureFistpage> getPictureFirstpageList(Integer count, Integer orderBy, Integer listType);
}
