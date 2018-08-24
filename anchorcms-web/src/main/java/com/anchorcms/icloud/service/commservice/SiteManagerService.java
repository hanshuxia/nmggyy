package com.anchorcms.icloud.service.commservice;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.commservice.SStiteManager;

/**
 * @Author zhouyq
 * @Date 2017/01/13
 * @Desc 政府网站维护服务层接口
 */
public interface SiteManagerService {
    /**
     * @param stiteName, address, pageNo, pageSize
     * @return
     * @Author zhouyq
     * @Date 2017/01/13
     * @Desc 政府网站分页后信息
     */
    public Pagination getList(String stiteName, String addrProvince, String address,String status, Integer pageNo, Integer pageSize);

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
     * @author zhouyq
     * @Date 2017/01/13
     * @description 根据id获取政府网站实体类
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
    public void siteMdySave(SStiteManager sSiteManager);

    /**
     * @return
     * @author zhouyq
     * @Date 2017/1/19
     * @description 公共服务二级页面政务导航获取地区网站信息
     */
    public String getPage(String stiteName, String addrProvince, String address, Integer pageNo, Integer pageSize);

    /**
     * @author dongxuecheng
     * @Date 2017/1/20 10:57
     * @return
     * @description设置发布与撤回的状态
     */
    public void setstatus(String stiteId,String status);

}
