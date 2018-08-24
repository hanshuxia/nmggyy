package com.anchorcms.icloud.service.supplychain.impl;


import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.supplychain.SSpareDemandDao;
import com.anchorcms.icloud.model.supplychain.*;
import com.anchorcms.icloud.service.supplychain.SSpareDemandService;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;



/**
 * @author gengwenlong
 * @Date 2017/1/4 19:07
 * @return
 */
@Service("sSpareDemandService")
@Transactional
public class SSpareDemandServiceImpl implements SSpareDemandService {
    @Resource
    SSpareDemandDao sSpareDemandDao;
    /**
     * @author gengwenlong
     * @Date 2017/1/4 19:07
     * @return报价
     */
    public Content save(SRepairQuote srq,String repairId,  String contentId, HttpServletRequest request,String[] attachmentPaths, String[] attachmentNames,
                        String[] attachmentFilenames, String[] picPaths, String[] picDescs, Content c, ContentExt ext, ContentTxt t, String nextUrl, Integer modelId,
                        Integer channelId, String textarea1, CmsUser user, Short charge, Integer typeId, boolean forMember,
                        HttpServletResponse response, ModelMap model){
        if(charge==null){
            charge= ContentCharge.MODEL_FREE;
        }
        saveContent(c, ext, t, channelId, typeId, null,true,user, forMember);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。
        Channel channel=channelMng.findById(channelId);
        c.addToChannels(channel);
        // 保存附件
       if (attachmentPaths != null && attachmentPaths.length > 0) {
            for (int i = 0, len = attachmentPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(attachmentPaths[i])) {
                    c.addToAttachmemts(attachmentPaths[i],
                            attachmentNames[i], attachmentFilenames[i]);
                }
            }
        }
        // 保存图片集
        if (picPaths != null && picPaths.length > 0) {
            for (int i = 0, len = picPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(picPaths[i])) {
                    c.addToPictures(picPaths[i], picDescs[i]);
                }
            }
        }
        srq.setContentId(contentId);
        srq.setDemandId(repairId);
        dao.save(srq);
        afterSave(c);
        return c;

//        return this.sSpareDemandDao.save(ssd);
    }
    /**
     * @param sRepairQuote 要保存的报价信息
     * @param repairQuoteObjsJsonStr 要保存报价信息的报价对像的json串
     * @author gengwenlong
     * @Date 2017/1/10 8:42
     * @return
     * 保存报价+报价对象list
     */
    public SRepairQuote save(SRepairQuote sRepairQuote, String repairQuoteObjsJsonStr) {
        List<SRepairAbility> sdqList = convertJsonToList(sRepairQuote, repairQuoteObjsJsonStr);
        sRepairQuote.setsRepairAbility(sdqList);
        return dao.save(sRepairQuote);
    }
    /**
     * @author gengwenlong
     * @Date 2017/1/10 8:41
     * @return
     * 将前台传入的json String转化为需求对象的List
     */
    private List<SRepairAbility> convertJsonToList(SRepairQuote sRepairQuote, String repairQuoteObjsJsonStr){
        JSONObject jsonObj = new JSONObject(repairQuoteObjsJsonStr);
        JSONArray jsonArr = (JSONArray) jsonObj.get("repairQuoteObjs");
        List<SRepairAbility> sDemandQuoteObjList = new ArrayList<SRepairAbility>();
        if(jsonArr==null||jsonArr.length()==0){
            return null;
        }
        //遍历传过来的json对象数组，取出值转换后置入对象，对象再置入list
        for(int i = 0; i<jsonArr.length(); i++){
            JSONObject demandQuoteJObj = jsonArr.getJSONObject(i);
            SRepairAbility dqObj = new SRepairAbility();
            String s = UUID.randomUUID().toString().replace("-","");//用来生成数据库的主键id
            dqObj.setDemandObjId(s);
            dqObj.setDemandRequestId(sRepairQuote.getDemandObjId());
            dqObj.setDemandId(demandQuoteJObj.getString("objId"));
            dqObj.setOffer(Double.parseDouble(demandQuoteJObj.getString("offer")));
            dqObj.setCreateId(sRepairQuote.getCreaterId());
            dqObj.setCreateDt(sRepairQuote.getCreateDt());
            sDemandQuoteObjList.add(dqObj);
        }
        return sDemandQuoteObjList;
    }
/**
 * @author gengwenlong
 * @Date 2017/1/9 10:27
 * 报价检索
 * @return
 */
    public List<SRepairDemand> getQuoteSearch(String repairId) {
        return dao.getQuoteSearch(repairId);
    }

    private Content saveContent(Content bean, ContentExt ext, ContentTxt txt,
                                Integer channelId, Integer typeId, Boolean draft,
                                Boolean contribute, CmsUser user, boolean forMember){
        Channel channel = channelMng.findById(channelId);
        bean.setChannel(channel);
        bean.setType(contentTypeMng.findById(typeId));
        bean.setUser(user);
        Byte userStep;
        if (forMember) {
            // 会员的审核级别按0处理
            userStep = 0;
        } else {
            CmsSite site = bean.getSite();
            userStep = user.getCheckStep(site.getSiteId());
        }
        // 流程处理
        if(contribute!=null&&contribute){
            bean.setStatus(ContentCheck.CONTRIBUTE);
        }else if (draft != null && draft) {
            // 草稿
            bean.setStatus(DRAFT);
        } else {
            if (userStep >= bean.getChannel().getFinalStepExtends()) {
                bean.setStatus(ContentCheck.CHECKED);
            } else {
                bean.setStatus(ContentCheck.CHECKING);
            }
        }
        // 是否有标题图
        bean.setHasTitleImg(!StringUtils.isBlank(ext.getTitleImg()));
        bean.init();
        // 执行监听器
        preSave(bean);
        contentDao.save(bean);
        contentExtMng.save(ext, bean);
        contentTxtMng.save(txt, bean);
        ContentCheck check = new ContentCheck();
        check.setCheckStep(userStep);
        contentCheckMng.save(check, bean);
        contentCountMng.save(new ContentCount(), bean);
        return bean;
    }

    private void preSave(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.preSave(content);
            }
        }
    }
    private void afterSave(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.afterSave(content);
            }
        }
    }
    /**
     * @author gengwenlong
     * @Date 2017/1/4 19:08
     * @return金牌老师傅展示列表
     */
    public Pagination getAllSearch(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr,
                                         int orderBy, int pageNo, int pageSize, String[] params) {
        return dao.getAllSearch(siteIds,typeIds,titleImg,recommend,title,attr,orderBy,pageNo,pageSize,params);
    }

    /**
     * @author gengwenlong
     * @Date 2017/1/4 19:09
     * @return金牌老师傅能力详细
     */
    public List<SRepairRes> getSearch(String repairId) {
        return dao.getSearch(repairId);
    }
    /**
     * @author gengwenlong
     * @Date 2017/1/4 19:10
     * @return金牌老师傅列表分页
     */
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, String status, String payType, String statusType) {
        return dao.getPageBySelf(channelId,siteId,modelId,memberId, pageNo, pageSize,releaseDt, deadlineDt, demandId,inquiryTheme,UserId,status,payType,statusType);

    }
    /**
     * @author gengwenlong
     * @Date 2017/2/13 15:01
     * @return
     * 获取金牌老师傅相似能力的自定义标签
     */
    public List<SRepairRes> getList(Integer count, Integer orderBy, Integer listType, String repairType) {
        return dao.getList(count,orderBy,listType,repairType);
    }
    /**
     * @author gengwenlong
     * @Date 2017/2/21 9:48
     * @return
     * 判断公司是否已对某维修需求报价
     */
    public Boolean hasQuoted(String demandId, String companyId) {
        return dao.hasQuoted(demandId,companyId);
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
    private SSpareDemandDao dao;
}
