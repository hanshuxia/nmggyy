package com.anchorcms.icloud.service.synergy;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;

/**
 * @author maocheng
 * @descript 头脑风暴Service
 * @date 2017/2/10
 */
public interface BrainStormService {

    /**
     * @Author maocheng
     * @Date 2017/2/10
     * @Desc 头脑风暴公告--保存
     */
    public  Content save(Content c, ContentExt ext, ContentTxt t, Integer channelId, Integer typeId, Short charge, CmsUser user, boolean b);

    /**
     * @Author wanjinyou
     * @Date 2017/2/9
     * @Desc 后台-互动专区--获取实体类
     */
    public Content byContentId(Integer id);

    /**
     * @Author maocheng
     * @Date 2017/2/14
     * @Desc 后台-互动专区--删除
     */
    public Content delete(Integer id);

    public void deletes(String ids);

    /**
     * @Author wanjinyou
     * @Date 2017/2/9
     * @Desc 后台-互动专区--列表
     */
    public Pagination getPageForMember(Integer queryChannelId, Integer siteId, Integer modelId, Integer userId, int cpn, int i, String author,String title, Byte status);

}
