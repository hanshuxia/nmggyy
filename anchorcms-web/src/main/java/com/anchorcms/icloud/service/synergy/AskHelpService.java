package com.anchorcms.icloud.service.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.common.page.Pagination;

import java.util.Date;

public interface AskHelpService {
    /**
     * @Author 闫浩芮
     * 查询需求列表
     * @Date 2016/12/23 0023 15:50
     */
    public Pagination getPageForMember(Integer queryChannelId, Integer siteId, Integer modelId, Integer userId, int cpn,
                                       int i, Date startDate, Date endDate,String author,String title, Byte status);
    /**
     * @Author 闫浩芮
     * 根据contentId查询对应的信息
     * @Date 2017/2/13 0013 16:29
     */
    public Content findById(Integer contentId);
    /**
     * @Author 闫浩芮
     * 置为通过
     * @Date 2017/2/18 0018 11:07
     */
    public void passMany(String ids);
    /**
     * @Author 闫浩芮
     * 置为驳回
     * @Date 2017/2/18 0018 11:07
     */
    public void deleteMany(String ids);
}