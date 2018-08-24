package com.anchorcms.icloud.service.supplychain.impl;


import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.supplychain.RepairDemandDao;
import com.anchorcms.icloud.dao.supplychain.SRepairInquiryDao;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairDemandObj;
import com.anchorcms.icloud.service.supplychain.RepairDemandService;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
*@Author 潘晓东
*@Date 2017/1/23 13:59
*@Return维修需求对象
*/
@Service
@Transactional
public class RepairDemandServiceImpl implements RepairDemandService {
    /**
     * @Author 潘晓东
     * @Date 2017/1/10 10:59
     * @Return根据ID查询维修需求
     */
    public SRepairDemand selectReapirDemand(String id) {
        SRepairDemand repairDemandEntity = repairDemandDao.selectReapirDemand(id);
        return repairDemandEntity;
    }

    /**
     * @return 根据ID查询维修需求信息
     * @author gengwenlong
     * @Date 2017/1/11 20:38
     */
    public List<SRepairDemand> selcetByRepairDemandId(String id) {
        List<SRepairDemand> list = repairDemandDao.selectReapirDemandById(id);
        return list;
    }


    /**
     * @return 保存维修需求信息
     * @author hansx
     * @Date 2017/1/2 0011 下午 4:43
     */
    public Content save(SRepairDemand srr, Content c, ContentExt ext, ContentTxt t, Integer channelId,
                        Integer typeId, CmsUser user, boolean forMember, String[] attachmentPaths,
                        String[] attachmentNames, String[] attachmentFilenames, String[] picPaths,
                        String[] picDescs, String SDemandObjs) {

        saveContent(c, ext, t, channelId, typeId, null, true, user, forMember);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。
        Channel channel = channelMng.findById(channelId);
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
        //CMS内容表主键id
        srr.setContentId(c.getContentId() + "");
        srr.setContent(c);
        //创建人id
        srr.setCreaterId(user.getUserId().toString());
        //创建时间
        srr.setCreateDt(new Date(System.currentTimeMillis()));
        //发布人id
        srr.setReleaseId(user.getUserId().toString());
        //发布时间
//        srr.setReleaseDt(new Date(System.currentTimeMillis()));
        //需求对象信息
        srr.setsRepairDemandObj(convertJStringToList(SDemandObjs, srr));
        this.repairDemandDao.save(srr);
        afterSave(c);
        return c;
    }

    /**
     * @author hansx
     * @Date 2017/1/2 0013 上午 10:00
     * @return
     * 将前台传入的jsonString转化为需求对象的List
     */
    private List<SRepairDemandObj> convertJStringToList(String SDemandObjs, SRepairDemand srr) {
        JSONObject jsonObj = new JSONObject(SDemandObjs);
        JSONArray jsonArr = (JSONArray) jsonObj.get("demandObjs");
        List<SRepairDemandObj> sDemandObjList = new ArrayList<SRepairDemandObj>();
        if (jsonArr == null || jsonArr.length() == 0) {
            return null;
        }
        Date createDate = new Date(System.currentTimeMillis());
        //遍历传过来的json对象数组，取出值转换后置入对象，对象再置入list
        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject demandJObj = jsonArr.getJSONObject(i);
            SRepairDemandObj dObj = new SRepairDemandObj();
            String uid = UUID.randomUUID().toString().replaceAll("-", "");//用来生成数据库的主键
            dObj.setRepairObjid(uid);
            dObj.setAddrProvince(srr.getAddrProvince());
            dObj.setAddrCity(srr.getAddrCity());
            dObj.setAddrStreet(srr.getAddrStreet());
            dObj.setAddrCounty(srr.getAddrCounty());
            dObj.setDeadlineDt(srr.getDeadlineDt());
            dObj.setCreaterId(srr.getCreaterId());
            dObj.setStartDt(srr.getStartDt());
            dObj.setRepairName(demandJObj.getString("repairNameStr"));
            dObj.setMachineType(demandJObj.getString("machineTypeStr"));
            dObj.setRepairNum(Double.parseDouble(demandJObj.getString("repairNumStr")));
            dObj.setRepairRequest(demandJObj.getString("repairRequestStr"));
            dObj.setExpectPrice(Double.parseDouble(demandJObj.getString("expectPriceStr")));
            dObj.setCreateDt(createDate);
            sDemandObjList.add(dObj);
        }
        return sDemandObjList;
    }

    /**
     * @return 保存content表信息
     * @author hansx
     * @Date 2017/1/2 0011 下午 4:44
     */
    private Content saveContent(Content bean, ContentExt ext, ContentTxt txt,
                                Integer channelId, Integer typeId, Boolean draft,
                                Boolean contribute, CmsUser user, boolean forMember) {
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
        if (contribute != null && contribute) {
            bean.setStatus(ContentCheck.CONTRIBUTE);
        } else if (draft != null && draft) {
            // 草稿
            bean.setStatus(DRAFT);
        } else {
            if (userStep >= bean.getChannel().getFinalStepExtends()) {
                bean.setStatus(ContentCheck.CHECKED);
            } else {
                bean.setStatus(ContentCheck.CHECKING);
            }
        }
        //暂时在新增时将content审核状态改为通过来生成全文检索索引
//        bean.setStatus(ContentCheck.CHECKED);
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


    @Resource
    private SRepairInquiryDao sRepairInquiryDao;
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
    @Autowired
    private RepairDemandDao repairDemandDao;
}
