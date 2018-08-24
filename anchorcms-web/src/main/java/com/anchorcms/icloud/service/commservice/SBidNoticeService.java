package com.anchorcms.icloud.service.commservice;

import com.anchorcms.icloud.model.commservice.SBidNotice;

/**
 * Created by Hansx on 2017/1/13 0013.
 *
 *  中标公告信息
 */
public interface SBidNoticeService {

    /**
     * @author hansx
     * @Date 2017/1/13 0013 上午 11:30
     * @return
     *  根据id获取招标预告信息
     */
    public SBidNotice findById(int id);

}
