package com.anchorcms.icloud.service.synergy.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.common.SMemberAddrDao;
import com.anchorcms.icloud.dao.synergy.SDemandCreateDao;
import com.anchorcms.icloud.dao.synergy.HotDemandDao;
import com.anchorcms.icloud.model.common.SMemberAddress;
import com.anchorcms.icloud.model.commservice.SBigDataDemandSign;
import com.anchorcms.icloud.model.commservice.SBigDataNews;
import com.anchorcms.icloud.model.commservice.SBigDataPolicy;
import com.anchorcms.icloud.model.commservice.SBigDataSign;
import com.anchorcms.icloud.model.synergy.SDemand;
import com.anchorcms.icloud.model.synergy.SDemandObj;
import com.anchorcms.icloud.service.common.SMemberAddrService;
import com.anchorcms.icloud.service.synergy.SDemandCreateService;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/* 
 * Copyright @ 2016 Shandong jxdinfo software co,ltd.
 * All right reserved.
 * @author: Gao Jiangning
 * @date: 2016/12/23 11:09
 * 添加需求的业务层实现类
 */
@Service
@Transactional
public class SDemandCreateServiceImpl implements SDemandCreateService {
    /**
     * @author: gao jiangning
     * @desciption 保存业务内容+cms内容
     */
    public Content save(SDemand bean, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths,
                        String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs,
                        Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember, String SDemandObjs) {
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
        bean.setContent(c);
        bean.setDemandObjList(convertJStringToList(SDemandObjs));
        //bean.setContentId(c.getContentId());
        sDemandCreateDao.save(bean);

        afterSave(c);
        return c;

    }
    /**
     * @author liuyang
     * @Date 2018/4/11 16:48
     * @return
     */
    public Content save2(SBigDataSign bean, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths,
                         String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs,
                         Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember) {
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
        bean.setContent(c);
        //bean.setContentId(c.getContentId());
        sDemandCreateDao.save2(bean);

        afterSave(c);
        return c;

    }



    /**
     * @author liuyang
     * @Date 2018/4/13 15:14
     * @return
     */
    public Content save3(SBigDataDemandSign bean, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths,
                         String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs,
                         Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember) {
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
        bean.setContent(c);
        //bean.setContentId(c.getContentId());
        sDemandCreateDao.save3(bean);

        afterSave(c);
        return c;

    }

    public Content saveBigdataNews(SBigDataNews bean, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths,
                                   String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs,
                                   Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember) {
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
        bean.setContent(c);
        //bean.setContentId(c.getContentId());
        sDemandCreateDao.saveBigdataNews(bean);

        afterSave(c);
        return c;
    }

    public Content saveBigdataPolicy(SBigDataPolicy bean, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths,
                                     String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs,
                                     Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember) {
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
        bean.setContent(c);
        //bean.setContentId(c.getContentId());
        sDemandCreateDao.saveBigdataPolicy(bean);

        afterSave(c);
        return c;
    }




    private Content saveContent(Content bean, ContentExt ext, ContentTxt txt,
                                Integer channelId,Integer typeId, Boolean draft,
                                Boolean contribute,CmsUser user, boolean forMember){
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
     * @author: gao jiangning
     * @desciption 将前台传入的json String转化为需求对象的List
     */
    private List<SDemandObj> convertJStringToList(String SDemandObjs){
        JSONObject jsonObj = new JSONObject(SDemandObjs);
        JSONArray jsonArr = (JSONArray) jsonObj.get("demandObjs");
        List<SDemandObj> sDemandObjList = new ArrayList<SDemandObj>();
        if(jsonArr==null||jsonArr.length()==0){
            return null;
        }
        Date createDate = new Date(System.currentTimeMillis());
        //遍历传过来的json对象数组，取出值转换后置入对象，对象再置入list
        for(int i = 0; i<jsonArr.length(); i++){
            JSONObject demandJObj = jsonArr.getJSONObject(i);
            SDemandObj dObj = new SDemandObj();
            dObj.setObjectName(demandJObj.getString("objectName"));
            dObj.setClassifyCode(demandJObj.getString("classifyCode"));
            dObj.setDemandCount(demandJObj.getDouble("demandCount"));
            dObj.setRemark(demandJObj.getString("remark"));
            dObj.setUnit(demandJObj.getString("unit"));
            if (demandJObj.has("expectPrice")) {
                if (!demandJObj.get("expectPrice").equals("")) {
                    dObj.setExpectPrice(demandJObj.getDouble("expectPrice"));
//                } else {
//                    dObj.setExpectPrice(demandJObj.getDouble("expectPrice"));
//                }
            }
            }
            dObj.setCreateDt(createDate);
            dObj.setUpdateDt(createDate);
            dObj.setDeFlag("1");
            //update时需要把demandObjId也set进去
            String demandObjIdStr = demandJObj.getString("demandObjId");
            if(demandObjIdStr != null && !"".equals(demandObjIdStr) && !"undefined".equals(demandObjIdStr)
                    && !"null".equals(demandObjIdStr)){
                dObj.setDemandObjid(Integer.parseInt(demandObjIdStr));
            }
            sDemandObjList.add(dObj);
        }
        return sDemandObjList;
    }
    /**
     * @Author 闫浩芮
     * @param demandId 文章id
     * @Date 2017/01/04
     * @Desc 更新需求信息
     */
    public SDemand byDemandId(Integer demandId) {
        return sDemandCreateDao.bySDemandId(demandId);
    }

    public void update(SDemand sDemand, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths,
                       String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs,
                       Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b,
                       String demandObjListJsonString) {
        //更新cms表内容
        Content nc=contentMng.update(c,ext,t,null,null,null,null,attachmentPaths,attachmentNames,attachmentFilenames,picPaths,
                picDescs,null,channelId,typeId,null,charge,null,user,b);
        //更新需求内容
        List<SDemandObj> sDemandObjList =convertJStringToList(demandObjListJsonString);
        int demandId = sDemand.getDemandId();
        for(SDemandObj o : sDemandObjList){
            o.setDemandId(demandId);
        }
        sDemand.setDemandObjList(sDemandObjList);
        sDemand.setContent(nc);
        Updater<SDemand> updater = new Updater<SDemand>(sDemand);
        yhrdao.updateByUpdater(updater);
        sDemandCreateDao.deleteOrphan();
    }

    /**
     * @Author zhouyq
     * @Date 2016/8/12
     * @Desc 获取联系人信息
     */
    public String getContactJson(Integer userId) {
        SMemberAddress sMemberAddress = sMemberAddrService.getContactInfo(userId);
        StringBuffer json = new StringBuffer();
        json.append("{\"contactObj\":{");
        if(sMemberAddress != null){
            json.append("\"shipAddressName\":\"").append(sMemberAddress.getShipAddressName()).append("\",");
            json.append("\"userName\":\"").append(sMemberAddress.getUserName()).append("\",");
            json.append("\"addr\":\"").append(sMemberAddress.getProvince()).append(sMemberAddress.getCity()).append(sMemberAddress.getCountry()).append(sMemberAddress.getAddress()).append("\",");
            json.append("\"mobile\":\"").append(sMemberAddress.getMobile()).append("\",");
            json.append("\"defAddr\":\"").append(sMemberAddress.getDefAddr()).append("\"");
        }
        json.append("}}");
        return json.toString();
    }

    /**
     * @Author zhouyq
     * @Date 2016/8/13
     * @Desc 获取联系人信息list
     */
    public List<SMemberAddress> getContactJsonList(Integer userId){
        return sMemberAddrDao.getContactJsonList(userId);
    }

    @Resource
    private HotDemandDao yhrdao;
    @Resource
    private ContentMng contentMng;
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
    private SDemandCreateDao sDemandCreateDao;
    @Resource
    private SMemberAddrService sMemberAddrService;
    @Resource
    private SMemberAddrDao sMemberAddrDao;
}
