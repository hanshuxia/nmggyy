package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.supplychain.RepairDemandMnageDao;
import com.anchorcms.icloud.dao.supplychain.SupplychainCreateDao;
import com.anchorcms.icloud.model.payment.SPOrder;
import com.anchorcms.icloud.model.payment.SPOrderObj;
import com.anchorcms.icloud.model.payment.SSupplychainOrder;
import com.anchorcms.icloud.model.payment.SSupplychainOrderObj;
import com.anchorcms.icloud.model.supplychain.*;
import com.anchorcms.icloud.model.synergy.SDemandObj;
import com.anchorcms.icloud.service.supplychain.RepairDemandMangeService;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * DESCRIPTION:众修需求管理Service
 * Author: ld
 * Date:2017/1/3.
 */
@Service
@Transactional
public class RepairDemandMangeServiceImp implements RepairDemandMangeService {

/**
 * @author ldong
 * @Date 2017/1/23 10:59
 * @return
 * 获取列表
 */

    public Pagination getRepairDemandPage(String repairType,String title, Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize, String state) {
        return RepairDemandMnageDao.getRepairDemandPage(repairType,title, null, memberId, memberId, false, false, Content.ContentStatus.all, null, siteId, modelId, channelId, 0, pageNo, pageSize, state);
    }


    /**
     * @return 需求管理列表的删除功能
     * @author ldong
     * @Date 2017/1/6 11:21
     */
    public SRepairDemand[] deleteRepairDemandByIds(String[] ids) {
        SRepairDemand[] beans = new SRepairDemand[ids.length];
        for (int i = 0, len = ids.length; i < len; i++) {
            beans[i] = this.RepairDemandMnageDao.selectReapirDemand(ids[i]);
            List<SRepairQuote> list =  beans[i].getsRepairQuote();
            Content contentBean = deleteContentById(beans[i].getContent().getContentId());
            this.RepairDemandMnageDao.deleteRepairDemandById(ids[i]);
            afterDelete(contentBean);
            for(SRepairQuote s :list) {
                this.RepairDemandMnageDao.deleteRepairQuoteByBean(s);
            }
           /* List<SRepairDemandObj> list = this.RepairDemandMnageDao.getDemandObjByDemandId(ids[i]);
            if (list != null && list.size() != 0) {
                for (SRepairDemandObj sdo : list) {
                    this.RepairDemandMnageDao.delSRepairDemandObj(sdo);
                }
            }*/

        }
        //this.RepairDemandMnageDao.setRepairQuoteNull();
        return beans;
    }

    /**
     * @return 删除RepairDemand 时删除 demand  content
     * @author ldong
     * @Date 2017/1/6 11:17
     */
    public Content deleteContentById(Integer id) {
        Content bean = contentDao.findById(id);
        // 执行监听器
        preDelete(bean);
        bean = contentDao.deleteById(id);
        // 执行监听器
        return bean;
    }

    /**
     * @return 改变demand的状态
     * @author ldong
     * @Date 2017/1/9 14:36
     */
    public SRepairDemand editCallBackRepairDemandByIds(String ids, String callBack) {
        SRepairDemand beans = this.RepairDemandMnageDao.editCallBackRepairDemandById(ids, callBack);
        return beans;
    }
/**
 * @author ldong
 * @Date 2017/1/23 10:58
 * @return
 * 获取下单列表信息
 */

    public List<SRepairQuote> getDoChoose(String ids) {
        return this.RepairDemandMnageDao.getDoChoose(ids);
    }

    /**
     * @return 更改需求表和报价表的状态呢
     * 适用于 有优选  取消优选
     * @author ldong
     * @Date 2017/1/9 14:31
     */
    public List<SRepairQuote> editChooseState(String ids, String callState, String demandId) {
        List<SRepairQuote> beans = new ArrayList<SRepairQuote>();
        String[] idss = ids.split(",");
        for (int i = 0, len = idss.length; i < len; i++) {
            beans.add(this.RepairDemandMnageDao.editChooseState(idss[i], callState));
        }
        if ("1".equals(callState)) {
            editCallBackRepairDemandByIds(demandId, "4");
        } else if ("0".equals(callState)) {
            SRepairDemand sr = getRepairDemandById(Integer.parseInt(demandId));
            List<SRepairQuote> bean = sr.getsRepairQuote();
            for (int i = 0; i < bean.size(); i++) {
                if (bean.get(i).getDeFlag() == "1") {
                    break;
                }
                if (i == bean.size() - 1) {
                    editCallBackRepairDemandByIds(demandId, "3");
                }
            }
        }
        return beans;
    }

    /**
     * @return 下单操作
     * @author ldong
     * @Date 2017/1/4 19:34
     */
    public void doOrder(String demandId, String demandQuoteId) {
        editCallBackRepairDemandByIds(demandId, "5");
        editStateBydemandObjId(demandQuoteId, "3");
    }
/**
 * @author ldong
 * @Date 2017/1/23 10:58
 * @return
 * 获取需求对象
 */

    public SRepairDemand getRepairDemandById(Integer demandId) {
        return this.RepairDemandMnageDao.getRepairDemandById(demandId);
    }

    /**
     * @return 撤回  发布  关闭
     * @author ldong
     * @Date 2017/1/9 14:08
     */
    public void setStateByRepairDemandByIds(CmsUser user, String demandId, String setState) {
       /* if ("7".equals(setState)) {
            SRepairDemand beans = this.RepairDemandMnageDao.editCallBackRepairDemandById(demandId, setState);
        }*/
/*else if ("2".equals(setState)) {
        if ("1".equals(setState)) {
            SRepairDemand demand = this.RepairDemandMnageDao.selectReapirDemand(demandId);
            List<SRepairQuote> list = demand.getsRepairQuote();
            if (list.size() != 0 && list != null) {
                for (SRepairQuote bean : list) {
                    bean.setSelectStatus("00");
                    bean.setDemandId(null);
                    this.RepairDemandMnageDao.updateSrepairQuote(bean);
                }
            }
            SRepairDemand beans = this.RepairDemandMnageDao.editCallBackRepairDemandById(demandId, setState);
        } */
         if ("2".equals(setState)) {
            SRepairDemand bean = this.RepairDemandMnageDao.selectReapirDemand(demandId);
            bean.setState("2");
            //发布时设置发布时间
            bean.setReleaseDt(new java.sql.Date(System.currentTimeMillis()));
            //bean.setReleaseId(user.getUserId().toString());
            bean.setReleaseId(user.getUsername());
            this.RepairDemandMnageDao.updateRepairDemand(bean);
        } else

        {
            SRepairDemand beans = this.RepairDemandMnageDao.editCallBackRepairDemandById(demandId, setState);
        }
    }


    /**
     * @return 改变订单列表的状态
     * @author ldong
     * @Date 2017/1/4 19:36
     */
    public SRepairQuote editStateBydemandObjId(String ids, String state) {
        SRepairQuote beans = this.RepairDemandMnageDao.editStateBydemandObjId(ids, state);
        return beans;
    }
/**
 * @author ldong
 * @Date 2017/1/23 10:56
 * @return
 * 获取下单teble信息
 */

    public List getGoOrder(String demandId, String demandQuoteID) {
        return this.RepairDemandMnageDao.getGoOrder(demandId, demandQuoteID);
    }
/**
 * @author ldong
 * @Date 2017/1/23 10:56
 * @return
 * 获取订单对象
 */

    public SRepairQuote getQuoteEntity(String demandQuoteID) {
        return this.RepairDemandMnageDao.getQuoteEntity(demandQuoteID);
    }

/**
 * @author ldong
 * @Date 2017/1/23 10:55
 * @return
 * 获取维修需求对象
 */

    public SRepairDemand selectReapirDemand(String id) {
        SRepairDemand repairDemandEntity = RepairDemandMnageDao.selectReapirDemand(id);
        return repairDemandEntity;
    }
/**
 * @author ldong
 * @Date 2017/1/23 10:55
 * @return获取需求对象list
 */

    public List<SRepairDemandObj> selcetByRepairDemandId(String id) {
        List<SRepairDemandObj> sRepairDemandObjList = RepairDemandMnageDao.selectRepairDemandObj(id);
        return sRepairDemandObjList;
    }

    public void updateRepairDemand(String[] indexId, SRepairDemand p, SRepairDemandObjPageBean bean) {
        this.RepairDemandMnageDao.updateRepairDemand(p);
        if (bean != null && bean.getRepairNameStr() != null &&
                !"".equals(bean.getRepairNameStr().trim())) {
            String[] repNameAarr = bean.getRepairNameStr().split(",");
            if (repNameAarr.length > 0) {
                for (int r = 0; r < repNameAarr.length; r++) {
                    if (!"".equals(repNameAarr[r].trim())) {
                        String uid;
                        if (r >= indexId.length) {
                            uid = UUID.randomUUID().toString().replaceAll("-", "");//用来生成数据库的主键
                        } else {
                            uid = indexId[r];
                        }

                        SRepairDemandObj po = new SRepairDemandObj();
                        po.setRepairObjid(uid);
                        po.setRepairId(p.getRepairId());
                        po.setAddrProvince(p.getAddrProvince());
                        po.setAddrCity(p.getAddrCity());
                        po.setAddrStreet(p.getAddrStreet());
                        po.setAddrCounty(p.getAddrCounty());
                        po.setCreateDt(new Date());
                        po.setDeadlineDt(po.getCreateDt());
                        po.setStartDt(p.getStartDt());
                        po.setRepairName(repNameAarr[r].trim());
                        if (bean.getExpectPriceStr() != null &&
                                !"".equals(bean.getExpectPriceStr().trim())) {
                            if (bean.getExpectPriceStr().split(",").length >= r && bean.getExpectPriceStr().split(",")[r] != null &&
                                    !"".equals(bean.getExpectPriceStr().split(",")[r].trim())) {
                                po.setExpectPrice(Double.parseDouble(bean.getExpectPriceStr().split(",")[r].trim()));
                            }

                        }
                        if (bean.getMachineTypeStr() != null &&
                                !"".equals(bean.getMachineTypeStr().trim())) {
                            if (bean.getMachineTypeStr().split(",").length >= r && bean.getMachineTypeStr().split(",")[r] != null &&
                                    !"".equals(bean.getMachineTypeStr().split(",")[r])) {
                                po.setMachineType(bean.getMachineTypeStr().split(",")[r].trim());
                            }

                        }
                        if (bean.getRepairNumStr() != null &&
                                !"".equals(bean.getRepairNumStr().trim())) {
                            if (bean.getRepairNumStr().split(",").length >= r && bean.getRepairNumStr().split(",")[r] != null &&
                                    !"".equals(bean.getRepairNumStr().split(",")[r].trim())) {
                                po.setRepairNum(Double.parseDouble(bean.getRepairNumStr().split(",")[r].trim()));
                            }

                        }

                        if (bean.getRepairRequestStr() != null &&
                                !"".equals(bean.getRepairRequestStr().trim())) {

                            if (bean.getRepairRequestStr().split(",").length >= r && bean.getRepairRequestStr().split(",")[r] != null &&
                                    !"".equals(bean.getRepairRequestStr().split(",")[r].trim())) {
                                po.setRepairRequest(bean.getRepairRequestStr().split(",")[r].trim());
                            }

                        }
                        this.RepairDemandMnageDao.updateDemandObj(po);

                    }
                }
            }
        }
    }

    /**
     * @return 监听器
     * @author ldong
     * @Date 2017/1/6 11:15
     */
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

    /**
     * @return 维修需求修改保存
     * @author ldong
     * @Date 2017/1/7 19:36
     */
    public Content repairDemandEditSave(SRepairDemand bean, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember, String SRepairdemandObj) {
        if (charge == null) {
            charge = ContentCharge.MODEL_FREE;
        }
        saveEditContent(c, ext, t, channelId, typeId, null, true, user, forMember);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。
        Channel channel = channelMng.findById(channelId);
        c.addToChannels(channel);
        // 保存附件
        c.setAttachments(new ArrayList<ContentAttachment>());
        if (attachmentPaths != null && attachmentPaths.length > 0) {
            for (int i = 0, len = attachmentPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(attachmentPaths[i])) {
                    c.addToAttachmemts(attachmentPaths[i],
                            attachmentNames[i], attachmentFilenames[i]);
                }
            }
        }
        // 保存图片集
        c.setPictures(new ArrayList<ContentPicture>());
        if (picPaths != null && picPaths.length > 0) {
            for (int i = 0, len = picPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(picPaths[i])) {
                    c.addToPictures(picPaths[i], picDescs[i]);
                }
            }
        }
        bean.setContent(c);
        bean.setsRepairDemandObj(convertJStringToList(SRepairdemandObj));
        // bean.setContentId(String.valueOf(c.getContentId()));
        this.RepairDemandMnageDao.saveRepairDemand(bean);
        afterSave(c);
        return c;

    }


    private Content saveEditContent(Content bean, ContentExt ext, ContentTxt txt,
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
        // 是否有标题图
        bean.setHasTitleImg(!StringUtils.isBlank(ext.getTitleImg()));
        bean.init();
        // 执行监听器
        preSave(bean);
        contentDao.save(bean);
        // contentExtMng.save(ext, bean);
        contentTxtMng.save(txt, bean);
        ContentCheck check = new ContentCheck();
        check.setCheckStep(userStep);
        contentCheckMng.save(check, bean);
        //内容计数
        // contentCountMng.save(new ContentCount(), bean);
        return bean;
    }

    /**
     * @return
     * @author ldong
     * @Date 2017/1/7 10:52
     * 保存维修需求
     */
    public Content save(SRepairDemand bean, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember, String SRepairdemandObj) {
        if (charge == null) {
            charge = ContentCharge.MODEL_FREE;
        }
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
        Content content = contentDao.findById(Integer.parseInt(bean.getContentId()));
        if (content != null) {
            preDelete(content);
            contentDao.deleteById(Integer.parseInt(bean.getContentId()));
            afterDelete(content);
        }
        // c.setContentId(Integer.parseInt(bean.getContentId()));
        bean.setContent(c);
        bean.setsRepairDemandObj(convertJStringToList(SRepairdemandObj));
        bean.setContentId(String.valueOf(c.getContentId()));
        this.RepairDemandMnageDao.saveRepairDemand(bean);
        afterSave(c);
        return c;

    }
    /**
     * @author gengwenlong
     * @Date 2017/1/19 14:58
     * @return
     * 需求信息修改
     */

    public void update(SRepairDemand bean, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember, String SRepairdemandObj) {
        //更新cms表内容
        Content nc=contentMng.update(c,ext,t,null,null,null,null,attachmentPaths,attachmentNames,attachmentFilenames,picPaths,
                picDescs,null,channelId,typeId,null,charge,null,user,forMember);
        //更新需求内容
        //List<SRepairDemandObj>objList =convertJStringToList(SRepairdemandObj);
        //String demandId = bean.getRepairId();
        //for(SRepairDemandObj o : objList){
        //    o.setRepairId(demandId);
        //}
        bean.setContent(nc);
        bean.setsRepairDemandObj(convertJStringToList(SRepairdemandObj));
        bean.setContentId(String.valueOf(c.getContentId()));
        this.RepairDemandMnageDao.saveRepairDemand(bean);
        this.RepairDemandMnageDao.deleteOrphan();
        //Updater<SRepairDemand> updater = new Updater<SRepairDemand>(bean);
        //this.RepairDemandMnageDao.updateByUpdater(updater);

    }
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
     * @author: ldong
     * @desciption 将前台传入的json String转化为需求对象的List
     */
    private List<SRepairDemandObj> convertJStringToList(String SDemandObjs) {
        JSONObject jsonObj = new JSONObject(SDemandObjs);
        JSONArray jsonArr = (JSONArray) jsonObj.get("demandObjs");
        List<SRepairDemandObj> sDemandObjList = new ArrayList<SRepairDemandObj>();
        if (jsonArr == null || jsonArr.length() == 0) {
            return null;
        }
        java.sql.Date createDate = new java.sql.Date(System.currentTimeMillis());
        //遍历传过来的json对象数组，取出值转换后置入对象，对象再置入list
        for (int i = 0; i < jsonArr.length(); i++) {
            JSONObject demandJObj = jsonArr.getJSONObject(i);
            SRepairDemandObj dObj = new SRepairDemandObj();
            if (demandJObj.getString("repairObjidStr") == null || demandJObj.getString("repairObjidStr").length() == 0) {
                String uid = UUID.randomUUID().toString().replaceAll("-", "");
                dObj.setRepairObjid(uid);
            } else {
                dObj.setRepairObjid(demandJObj.getString("repairObjidStr"));
            }
            dObj.setRepairName(demandJObj.getString("repairNameStr"));
            dObj.setMachineType(demandJObj.getString("machineTypeStr"));
            dObj.setRepairNum(Double.parseDouble(demandJObj.getString("repairNumStr")));
            dObj.setRepairRequest(demandJObj.getString("repairRequestStr"));
            dObj.setExpectPrice(Double.parseDouble(demandJObj.getString("expectPriceStr")));
            // dObj.setCreateDt(createDate);
            // dObj.setUpdateDt(createDate);
            dObj.setDeFlag("1");
            sDemandObjList.add(dObj);
        }
        return sDemandObjList;
    }

    /**
     * @return 获取优选界面的list
     * @author ldong
     * @Date 2017/1/10 11:00
     */
    public String getQuoteListJson(Integer demandId, Integer pageNo, int pageSize) {
        Pagination qListPage = this.RepairDemandMnageDao.getQuoteList(demandId, pageNo, pageSize);
        return convertRepairQuoteList(qListPage, demandId);
    }

    /**
     * @return 转换为优选界面需要的json
     * @author ldong
     * @Date 2017/1/10 11:02
     */
    private String convertRepairQuoteList(Pagination qListPage, Integer demandId) {
        List<SRepairQuote> qList = (List<SRepairQuote>) qListPage.getList();
        StringBuffer json = new StringBuffer();
        java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
        nf.setGroupingUsed(false);
        json.append("{\"quotes\":[");
        if (qList != null && qList.size() > 0) {
            for (SRepairQuote sq : qList) {
                json.append("{\"quoteId\":\"").append(sq.getDemandObjId()).append("\",");
                json.append("\"demandId\":\"").append(sq.getDemandId()).append("\",");
                json.append("\"selected\":\"").append(sq.getSelectStatus()).append("\",");//是否被优选
                json.append("\"companyCode\":\"").append(sq.getCompany().getCompanyCode()).append("\",");
                json.append("\"companyName\":\"").append(sq.getCompany().getCompanyName()).append("\",");
                json.append("\"companyType\":\"").append(sq.getCompany().getCompanyType()).append("\",");
                json.append("\"companyScale\":\"").append(sq.getCompany().getCompanyScale()).append("\"},");
            }
            json.deleteCharAt(json.length() - 1);//删除最后的逗号
        }
        json.append("],").append("\"count\":\"").append(qListPage.getTotalCount()).append("\",");
        json.append("\"pageNo\":\"").append(qListPage.getPageNo()).append("\",");
        json.append("\"totalPage\":\"").append(qListPage.getTotalPage()).append("\",");
        json.append("\"demandId\":\"").append(nf.format(demandId)).append("\"");
        json.append("}");
        return json.toString();
    }

    /**
     * @return 批量优选
     * @author ldong
     * @Date 2017/1/10 15:19
     */
    public int selectQuotes(String quoteIds) {
        if (quoteIds == null || "".equals(quoteIds)) {
            return 0;
        }
        if (',' == (quoteIds.charAt(quoteIds.length() - 1))) {
            quoteIds = quoteIds.substring(0, quoteIds.length() - 1);
        }
        return this.RepairDemandMnageDao.selectQuotes(quoteIds);
    }

    /**
     * 批量对报价取消优选
     *
     * @param quoteIds
     * @return
     */
    public int cancelSelectedQuotes(String quoteIds) {
        if (quoteIds == null || "".equals(quoteIds)) {
            return 0;
        }
        if (',' == (quoteIds.charAt(quoteIds.length() - 1))) {
            quoteIds = quoteIds.substring(0, quoteIds.length() - 1);
        }
        return this.RepairDemandMnageDao.cancelSelectedQuotes(quoteIds);
    }


    /**
     * 获得 下单页面表格 需要的 json
     *
     * @param quoteId
     * @return
     */
    public String getOrderListJson(String quoteId) {
        SRepairQuote quote = this.RepairDemandMnageDao.getQuoteEntity(quoteId);
        SRepairDemand demand = quote.getDemand();
        List<SRepairDemandObj> demandObjList = demand.getsRepairDemandObj();
        Map<String, Double> quotePriceMap = this.getQuotePriceMap(quoteId);
        StringBuffer json = new StringBuffer();
        json.append("{\"objs\":[");
        if (demandObjList != null && demandObjList.size() > 0) {
            for (SRepairDemandObj demandObj : demandObjList) {
                Double qPrice = quotePriceMap.get(demandObj.getRepairObjid());
                json.append("{\"objName\":\"").append(demandObj.getRepairName()).append("\",");
                json.append("\"objId\":\"").append(demandObj.getRepairObjid()).append("\",");
                json.append("\"objCode\":\"").append(demandObj.getMachineType()).append("\",");
                json.append("\"amount\":\"").append(new DecimalFormat("########").format(new BigDecimal(demandObj.getRepairNum()))).append("\",");
                json.append("\"unit\":\"").append(demandObj.getAddrProvince() + demandObj.getAddrCity() + demand.getAddrCity() + demandObj.getAddrStreet()).append("\",");
                json.append("\"expPrice\":\"").append(new DecimalFormat("########.##").format(new BigDecimal(demandObj.getExpectPrice().toString()))).append("\",");
                json.append("\"qPrice\":\"").append((qPrice == null) ? "未报价" : new DecimalFormat("########.##").format(new BigDecimal(qPrice))).append("\"},");
            }
            json.deleteCharAt(json.length() - 1);
        }
        json.append("],").append("\"company\":\"").append(quote.getCompany().getCompanyName()).append("\",");
        json.append("\"quoteId\":\"").append(quoteId).append("\"}");
        return json.toString();
    }


    /**
     * @return 获取报价的信息
     * @author ldong
     * @Date 2017/1/10 17:39
     */
    private Map<String, Double> getQuotePriceMap(String quoteId) {
        List<SRepairAbility> list = this.RepairDemandMnageDao.getQuotePrice(quoteId);
        Map<String, Double> map = new HashMap<String, Double>();
        for (SRepairAbility m : list) {
            Double offer = m.getOffer();
            String ObjId = m.getDemandId();
            map.put(ObjId, offer);
        }
        return map;
    }

    /**
     * 进行下单的业务逻辑处理【1.update报价表(状态位+分配量) 2.update需求表状态为已下单】
     *
     * @param orderJson
     * @param quoteId
     * @return
     * @Author ldong
     */
    public boolean excuteOrder(String orderJson, String quoteId) {
        JSONObject oJson = new JSONObject(orderJson);
        JSONArray jsonArr = (JSONArray) oJson.get("distribute");
        //TODO:改一下，根据demandObjId 和 quoteId 先拉出 quoteObj, 放好数量，存list里，再存入quote, update;
        // Map<Integer,Double> distriMap = new HashMap<Integer, Double>();
      /*  if(jsonArr==null||jsonArr.length()<1){
            return false;
        }
        for(int i = 0; i<jsonArr.length(); i++){
            JSONObject distribute = jsonArr.getJSONObject(i);
             distriMap.put(distribute.getInt("objId"),distribute.getDouble("mount"));
        }*/
        // SRepairQuote quote = this.RepairDemandMnageDao.getQuoteEntity(quoteId);
        // quote.setSelectedStatus("2");
        // quote.setQuoteMountedMap(distriMap);
        // Updater<SRepairQuote> updater = new Updater<SRepairQuote>(quote);
        SRepairQuote quote = this.RepairDemandMnageDao.editStateBydemandObjId(quoteId, "2");
         //下单将需求的状态改为5
        //SRepairDemand demand = quote.getDemand();
        //demand.setState("5");
        //this.RepairDemandMnageDao.updateRepairDemand(demand);

        // Updater<SDemand> updater1 = new Updater<SDemand>(demand);
        // demand = demandDao.updateByUpdater(updater1);

        if ("2".equals(quote.getSelectStatus()) /*&& "3".equals(demand.getState())*/) {
            return true;
        } else {
            return false;
        }
    }


    public Integer setEvaluteValue(String demandObjId,String evaluteValue){
        return RepairDemandMnageDao.setEvaluteValue(demandObjId,evaluteValue);
    }
    /**
     * @author liuyang
     * @Date 2017/5/4 16:06
     * @return
     */
    public SRepairDemandObj byDemandObjId(String demandId) {
        return RepairDemandMnageDao.byDemandObjId(demandId);
    }
    public void saveOrder(SSupplychainOrder buy) {
        RepairDemandMnageDao.save(buy);
    }

    public void saveSPOrderObj(SSupplychainOrderObj o) {
        RepairDemandMnageDao.saveSPOrderObj(o);
    }


    @Resource
    private SupplychainCreateDao supplychainCreateDao;
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
    private RepairDemandMnageDao RepairDemandMnageDao;
    @Resource
    private ContentMng contentMng;
}
