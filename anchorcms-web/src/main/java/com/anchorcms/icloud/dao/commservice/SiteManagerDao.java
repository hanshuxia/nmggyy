package com.anchorcms.icloud.dao.commservice;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.commservice.SStiteManager;

/**
 * @Author zhouyq
 * @Date 2017/01/13
 * @Desc 政府网站Dao层
 */
public interface SiteManagerDao {
    /**
     * @param stiteName, address, pageNo, pageSize
     * @return
     * @Author zhouyq
     * @Date 2017/01/13
     * @Desc 获取政府网站分页后信息
     */
    public Pagination getPage(String stiteName, String addrProvince, String address,String status, Integer pageNo, Integer pageSize);

    /**
     * @param stiteId
     * @return
     * @Author zhouyq
     * @Date 2017/01/13
     * @Desc 根据id删除政府网站记录
     */
    public void delSiteById(String stiteId);

    /**
     * @param stiteId
     * @return
     * @Author zhouyq
     * @Date 2017/01/13
     * @Desc 根据id获取政府网站实体类
     */
    public SStiteManager getSiteManagerEntity(String stiteId);

    /**
     * @param sSiteManager
     * @return
     * @Author zhouyq
     * @Date 2017/01/13
     * @Desc 政府网站新增保存
     */
    public void siteAddSave(SStiteManager sSiteManager);

    /**
     * @param sSiteManager
     * @return
     * @author zhouyq
     * @Date 2017/1/14
     * @description 政府网站修改保存
     */
    public int siteMdySave(SStiteManager sSiteManager);

    /**
     * @author dongxuecheng
     * @Date 2017/1/20 10:57
     * @return
     * @description设置发布与撤回的状态
     */
    public int setstatus(String stiteId, String status);
}
