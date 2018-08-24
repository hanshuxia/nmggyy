package com.anchorcms.icloud.service.cloudcenter;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.cloudcenter.SIcloudCenter;

import java.util.Date;
import java.util.List;

/**
 * @author 李利民
 * @descript 云计算机中心保存
 * @date 2017/1/4 11:04
 */
public interface CloudCenterService {
    Content save(SIcloudCenter sd, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b);


    /**
     * @author wanjinyou
     * @descript 云计算机中心列表展示
     * @date 2017/1/7 10:31
     */
    public Pagination getPageForCenter(Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize,
                                       Integer centerId, String centerName,String addrStreet,Date startDate, Date endDate);

    /**
     * @author wanjinyou
     * @descript 云计算机中心--编辑
     * @date 2017/1/7 13:16
     */
    public SIcloudCenter findById(Integer id);



    /**
     * @author wanjinyou
     * @descript 云计算机中心--编辑更新
     * @date 2017/1/7 13:50
     */
    public SIcloudCenter update(Integer id, SIcloudCenter sd, String detailDesc, Integer channelId, CmsUser user, Short charge, boolean b,String[] picPaths, String[] picDescs);
    /**
     * @author wanjinyou
     * @descript 云计算机中心--删除
     * @date 2017/1/7 16:30
     */
    public SIcloudCenter deleteById(Integer id);
    /**
     *@Author jsz
     *@Date 2017/1/19
     *@desc  云计算中心标签查询
     */
    public List<SIcloudCenter> getList(Integer count, Integer orderBy, Integer areaType);
    /**
     * 批量删除
     * @Author 闫浩芮
     * @param demandIdsStr 需要批量操作的Id字符串
     * @Date 2017/2/20 0020 10:37
     */
    public void deleteMany(String demandIdsStr);
}
