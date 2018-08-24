package com.anchorcms.icloud.service.synergy.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.dao.synergy.HotDemandDao;
import com.anchorcms.icloud.model.synergy.SDemand;
import com.anchorcms.icloud.service.synergy.SHotDemandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class SHotDemandServiceImpl implements SHotDemandService {
    /**
     * @Author Yhr
     * 分页查询需求列表
     * @Date 2016/12/23 0023 15:50
     */
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId,int pageNo, int pageSize,
                                       Date releaseDt,Date deadlineDt, Integer demandId,String inquiryTheme,
                                       String status,String recommendedType,String addrCity,String addrCounty,
                                       String addrStreet,String operatorId) {
        return dao.getPageBySelf(channelId,siteId,modelId,pageNo, pageSize,releaseDt, deadlineDt, demandId,inquiryTheme,
                status,recommendedType,addrCity,addrCounty,addrStreet,operatorId);
    }
    /**
     * @Author 闫浩芮
     * 更新需求信息的status
     * @Date 2016/12/27 0027 16:03
     */
    public void  update(Integer demandId){
        SDemand sDemand = dao.findById(demandId);
        if("0".equals(sDemand.getRecommendedType())){
            sDemand.setRecommendedType("1");
        }else {
            sDemand.setRecommendedType("0");
        }
        Updater<SDemand> updater = new Updater<SDemand>(sDemand);
        dao.updateByUpdater(updater);

    }
    /**
     * @author jsz
     * @date 2017/1/22 13:47
     * @desc 自定义标签——热门需求推荐列表获取
     **/
    public List<SDemand> getList(Integer count, Integer orderBy, Integer listType) {
        return dao.getList(count, orderBy, listType);
    }

    /**
     * @Author 闫浩芮
     * 管理员置为通过需求
     * @Date 2017/2/17 0017 18:39
     */
    public void passMany(String demandIdsStr) {
        if(',' == (demandIdsStr.charAt(demandIdsStr.length()-1))){
            demandIdsStr = demandIdsStr.substring(0,demandIdsStr.length()-1);
        }
        dao.passMany(demandIdsStr);
    }
    /**
     * @Author 闫浩芮
     * 管理员置为驳回需求
     * @Date 2017/2/17 0017 18:39
     */
    public void rejectMany(String demandIdsStr) {
        if(',' == (demandIdsStr.charAt(demandIdsStr.length()-1))){
            demandIdsStr = demandIdsStr.substring(0,demandIdsStr.length()-1);
        }
        dao.rejectMany(demandIdsStr);
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
    private HotDemandDao dao;
}
