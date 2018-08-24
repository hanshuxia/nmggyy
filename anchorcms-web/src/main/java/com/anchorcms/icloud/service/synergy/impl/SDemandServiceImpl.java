package com.anchorcms.icloud.service.synergy.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.synergy.CompanyDao;
import com.anchorcms.icloud.dao.synergy.SDemandDao;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SDemand;
import com.anchorcms.icloud.model.synergy.SDemandObj;
import com.anchorcms.icloud.service.synergy.SDemandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class SDemandServiceImpl implements SDemandService {
    /**
     * @Author Yhr
     * 查询需求列表
     * @Date 2016/12/23 0023 15:50
     */
    public Pagination getPageForMember(Integer canshu, Integer siteId, Integer modelId, Integer UserId,
                                       Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                                       String selectedStatus, String payType,String statusType) {
        return dao.getPageBySelfCompany(canshu,siteId,modelId,memberId, pageNo, pageSize,releaseDt, deadlineDt, demandId,inquiryTheme,UserId,selectedStatus,payType,statusType);
    }
    public Pagination getPageForMember2(Integer canshu, Integer siteId, Integer modelId, Integer UserId,
                                       Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                                       String selectedStatus, String payType,String statusType) {
        return dao.getPageBySelfCompany2(canshu,siteId,modelId,memberId, pageNo, pageSize,releaseDt, deadlineDt, demandId,inquiryTheme,UserId,selectedStatus,payType,statusType);
    }
    /**
     * @Author iangshuzhong
     * @param demandId 文章id
     * @Date 2016/12/21
     * @Desc 需求管理详情
     */
    public SDemand byDemandId(Integer demandId) {
        return dao.bySDemandId(demandId);
    }
    public SDemandObj byDemandObjId(Integer demandId) {
        return dao.byDemandObjId(demandId);
    }
    /**
     * @Author 闫浩芮
     * 更新需求信息的status
     * @Date 2016/12/27 0027 16:03
     */
    public void  update(Integer demandId,String a){
        SDemand sDemand = dao.findById(demandId);
        if("3".equals(a)||a ==null){ // 询价中状态进行废止
            sDemand.setStatusType("5");
            int sDemandSelectedStatus = sDemand.getDemandQuoteList().size();
            if (sDemandSelectedStatus > 1) {
                for (int i  = 0; i < sDemandSelectedStatus; i ++) {
                    sDemand.getDemandQuoteList().get(i).setSelectedStatus("");
                }
            } else {
                sDemand.getDemandQuoteList().get(0).setSelectedStatus("");
            }
        }
        if (sDemand.getStatusType() != null) { // 需求列表中任何tab进行的操作
            switch (Integer.parseInt(sDemand.getStatusType())) {
                case 1:
                    sDemand.setStatusType("2");
                    break;
                case 2:
                    sDemand.setStatusType("1");
                    break;
                case 3:
                    sDemand.setStatusType("1");
                    break;
                case 4:
                    sDemand.setStatusType("5");
                    break;
            }
        }
        Updater<SDemand> updater = new Updater<SDemand>(sDemand);
        dao.updateByUpdater(updater);

    }
    /**
     * @Author 闫浩芮
     * 删除需求信息
     * @Date 2016/12/29 0029 10:55
     */
    public SDemand  delete(Integer demandId){
        SDemand bean = dao.findById(demandId);//获取能力实体类
        Content contentBean = deleteContentById(bean.getContent().getContentId());
        bean = dao.delete(bean);// 执行删除
        afterDelete(contentBean);
        return bean;
    }

    public Content deleteContentById(Integer id) {
        Content bean = contentDao.findById(id);
        // 执行监听器
        preDelete(bean);
        bean = contentDao.deleteById(id);
        // 执行监听器
        return bean;
    }
    private void preDelete(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.preDelete(content);
            }
        }
    }
    private void afterDelete(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.afterDelete(content);
            }
        }
    }
    /**@AUTHER LLILIMIN
     * 企业能力展示
     * @param id
     * @return
     */
    public SCompany byCompanyId(String id) {
        return cDao.getCompanyBy(id);
    }
    /**
     * @author ly
     * @date 2017/1/9 13:47
     * @desc 自定义标签——能力列表获取
     **/
    public List<SDemand> getList(Integer count, Integer orderBy, Integer listType,Integer overDate,String demandType) {
        return dao.getList(count, orderBy, listType,overDate,demandType);
    }

    /**
     * @author liuyang
     * @Date 2017/6/2 13:45
     * @return
     */
    public Pagination getDevicePage(Integer canshu, Integer siteId, Integer modelId, Integer UserId,
                                       Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                                       String selectedStatus, String payType,String statusType) {
        return dao.getPageByDeviceCompany(canshu,siteId,modelId,memberId, pageNo, pageSize,releaseDt, deadlineDt, demandId,inquiryTheme,UserId,selectedStatus,payType,statusType);
    }
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private List<ContentListener> listenerList;
    @Resource
    private ContentTxtMng contentTxtMng;
    @Resource
    private ContentExtMng contentExtMng;
    @Resource
    private ContentCountMng contentCountMng;
    @Resource
    private ContentCheckMng contentCheckMng;
    @Resource
    private ContentDao contentDao;
    @Resource
    private SDemandDao dao;
    @Resource
    private CompanyDao cDao;
}
