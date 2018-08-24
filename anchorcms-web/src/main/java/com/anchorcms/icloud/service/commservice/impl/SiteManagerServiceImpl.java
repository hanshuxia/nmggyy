package com.anchorcms.icloud.service.commservice.impl;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.commservice.SiteManagerDao;
import com.anchorcms.icloud.model.commservice.SStiteManager;
import com.anchorcms.icloud.service.commservice.SiteManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author zhouyq
 * @Date 2017/01/13
 * @Desc 政府网站服务实现类
 */
@Service
@Transactional
public class SiteManagerServiceImpl implements SiteManagerService {
    @Resource
    private SiteManagerDao dao;

    /**
     * @param stiteName, address, pageNo, pageSize
     * @return
     * @Author zhouyq
     * @Date 2017/01/13
     * @Desc 政府网站分页后信息
     */
    public Pagination getList(String stiteName, String addrProvince, String address,String status, Integer pageNo, Integer pageSize) {
        Pagination page = dao.getPage(stiteName, addrProvince, address, status, pageNo, pageSize);
        return page;
    }

    /**
     * @return
     * @author zhouyq
     * @Date 2017/1/19
     * @description 公共服务二级页面政务导航获取地区网站信息
     */
    public String getPage(String stiteName, String addrProvince, String address, Integer pageNo, Integer pageSize) {
        Pagination p = dao.getPage(stiteName, addrProvince, address, "3", pageNo, pageSize);
        return convertSiteInfoList(p);
    }
    /**
     * @author dongxuecheng
     * @Date 2017/1/20 10:57
     * @return
     * @description设置发布与撤回的状态
     */
    public void setstatus(String stiteId, String status) {
        dao.setstatus(stiteId,status);
    }

    /**
     * @return 转换为公共服务二级页面政务导航获取地区网站信息需要的json
     * @author zhouyq
     * @Date 2017/1/19
     */
    public String convertSiteInfoList(Pagination qListPage) {
        List<SStiteManager> qList = (List<SStiteManager>) qListPage.getList();
        StringBuffer json = new StringBuffer();
        json.append("{\"SStiteManagers\":[");
        if (qList != null && qList.size() > 0) {
            for (SStiteManager sq : qList) {
                json.append("{\"stiteName\":\"").append(sq.getStiteName()).append("\",");
                json.append("\"stiteAddress\":\"").append(sq.getStiteAddress()).append("\"},");
            }
            json.deleteCharAt(json.length() - 1);//删除最后的逗号
        }
        json.append("]").append("}");
        return json.toString();
    }

    /**
     * @param stiteId
     * @return
     * @Author zhouyq
     * @Date 2017/01/13
     * @Desc 根据id删除政府网站记录
     */
    public void delSiteById(String stiteId) {
        dao.delSiteById(stiteId);
    }

    /**
     * @param stiteId
     * @return
     * @author zhouyq
     * @Date 2017/01/13
     * @description 根据id获取政府网站实体类
     */
    public SStiteManager getSiteManagerEntity(String stiteId) {
        return dao.getSiteManagerEntity(stiteId);
    }

    /**
     * @param sSiteManager
     * @return
     * @Author zhouyq
     * @Date 2017/01/13
     * @Desc 政府网站新增保存
     */
    public void siteAddSave(SStiteManager sSiteManager) {
        dao.siteAddSave(sSiteManager);
    }

    /**
     * @param sSiteManager
     * @return
     * @author zhouyq
     * @Date 2017/1/14
     * @description 政府网站修改保存
     */
    public void siteMdySave(SStiteManager sSiteManager) {
        dao.siteMdySave(sSiteManager);
    }

}
