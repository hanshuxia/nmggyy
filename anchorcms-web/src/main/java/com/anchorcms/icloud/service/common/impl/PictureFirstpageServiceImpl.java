package com.anchorcms.icloud.service.common.impl;

import com.anchorcms.icloud.dao.common.PictureFirstpageDao;
import com.anchorcms.icloud.model.commservice.PictureFistpage;
import com.anchorcms.icloud.service.common.PictureFirstpageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ${耿文龙} on 2017/2/10.
 */
@Service
public class PictureFirstpageServiceImpl implements PictureFirstpageService {
    /**
     * @author gengwenlong
     * @Date 2017/2/10 13:54
     * @return
     * 自定义标签-图片轮播
     */
    public List<PictureFistpage> getPictureFirstpageList(Integer count, Integer orderBy, Integer listType) {
        return pictureFirstpageDao.getPictureFistpageList(count, orderBy, listType);
    }
    @Resource
    private PictureFirstpageDao pictureFirstpageDao;
}
