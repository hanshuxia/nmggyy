package com.anchorcms.icloud.service.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;

import java.util.Date;

/**
 * @Author wanjinyou
 * @Date 2017/2/9
 * @Desc 互动专区
 */
public interface InteractAreaService {

    /**
     * @Author wanjinyou
     * @Date 2017/2/9
     * @Desc 互动专区--保存
     */
    public Content save(Content c, ContentExt ext, ContentTxt t, Integer channelId, Integer typeId, Short charge, CmsUser user, boolean b);
    /**
     * @Author wanjinyou
     * @Date 2017/2/9
     * @Desc 后台-互动专区--列表
     */
    public Pagination getPageForMember(Integer queryChannelId, Integer siteId, Integer modelId, Integer userId, int cpn, int i, Date startDate, Date endDate, String companyName,String title, Byte status);

    /**
     * @Author wanjinyou
     * @Date 2017/2/9
     * @Desc 后台-互动专区--通过
     */
    public Content pass(Integer id,Integer channelId, CmsUser user, Short charge, boolean b);

    public void passTotal(String ids,Integer channelId, CmsUser user, Short charge, boolean b);

    /**
     * @Author wanjinyou
     * @Date 2017/2/9
     * @Desc 后台-互动专区--获取实体类
     */
    public Content byContentId(Integer id);

    /**
     * @Author wanjinyou
     * @Date 2017/2/9
     * @Desc 后台-互动专区--删除
     */
    public Content delete(Integer id);
}