package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.assist.CmsCommentMng;
import com.anchorcms.cms.service.assist.CmsFileMng;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.supplychain.SupplychainCreateDao;
import com.anchorcms.icloud.dao.synergy.HotDemandDao;
import com.anchorcms.icloud.model.supplychain.*;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.service.supplychain.SupplychainCreateService;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * Created by machao on 2016/12/21.
 * 备品备件查询
 */
@Service
@Transactional
public class SupplychainCreateServiceImpl implements SupplychainCreateService {

    public List<SSpare> getList() {
        return supplychainCreateDao.getAll();
    }

  /*  public SRepairRes addResource(SRepairRes srr) {

         return this.supplychainCreateDao.addResource(srr);
     }*/

    /**
     * @param bean
     * @param c
     * @param picDescs
     */

    public Content save(SRepairRes bean, Content c, ContentExt ext, ContentTxt t, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, boolean forMember) {

        saveContent(c, ext, t, channelId, typeId, null,true,user, forMember);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。
        Channel channel=channelMng.findById(channelId);
        c.addToChannels(channel);
        // 保存附件
       /* if (attachmentPaths != null && attachmentPaths.length > 0) {
            for (int i = 0, len = attachmentPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(attachmentPaths[i])) {
                    c.addToAttachmemts(attachmentPaths[i],
                            attachmentNames[i], attachmentFilenames[i]);
                }
            }
        }*/
        // 保存图片集
        if (picPaths != null && picPaths.length > 0) {
            for (int i = 0, len = picPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(picPaths[i])) {
                    c.addToPictures(picPaths[i], picDescs[i]);
                }
            }
        }
        bean.setContent(c);
        bean.setContentId(c.getContentId()+"");
        this.supplychainCreateDao.addResource(bean);
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

    /**
     *
     * @param title
     * @param channelId
     * @param siteId
     * @param modelId
     * @param memberId
     * @param pageNo
     *            页码
     * @param pageSize
     *            每页大小
     * @return
     */
    public Pagination getPageForMember(String title, Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize) {
        return supplychainCreateDao.getPage(title, null,memberId,memberId, false, false, Content.ContentStatus.all, null, siteId,modelId,  channelId, 0, pageNo,pageSize);
    }

    public int insertsSpareDemandEntity(SSpareDemand sSpareDemand) {
        return supplychainCreateDao.insertsSpareDemandEntity(sSpareDemand);
    }
    public SSpareDemand updateSpareDemandEntity(SSpareDemand su) {
        return supplychainCreateDao.updateSpareDemandEntity(su);
    }

    public Content supplychain_save(SSpareDemand bean, Content c, ContentExt ext, ContentTxt t, Integer channelId, Integer typeId, CmsUser user, boolean b) {

        saveContent(c, ext, t, channelId, typeId, null, true, user, b);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。
        Channel channel = channelMng.findById(channelId);
        c.addToChannels(channel);
        supplychainCreateDao.insertsSpareDemandEntity(bean);
        afterSave(c);
        return c;
    }

    /**
     * @return
     */
    public List<SSpareDemand> getListSpare(){
        return supplychainCreateDao.getAllSpare();
    }
    public List<SSpareDemand> searchList(String theme){
        return supplychainCreateDao.search(theme);
    }

    public List<SSpareDemand> manageList() {
        return supplychainCreateDao.BpbjqgManagelist();
    }

    public List<SSpareDemand> ManageSearchList(String theme, String type){
        return supplychainCreateDao.BpbjqgManagelistSearch(theme,type);
    }
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/23 17:06
     * @return
     * 备品备件求购管理明细预览
     */
    public List<SSpareDemand> SearchList(String uid) {
        return supplychainCreateDao.ManagelistSearch(uid);
    }

    /**
     * @author 苏和 13739980760
     * @Date 2017/1/23 17:04
     * @return
     * 备品备件求购管理编辑（取数据）
     */
    public SSpareDemand SearchLis(String uid) {
        return this.supplychainCreateDao.ManagelistSearc(uid);
    }


    /**
     * @author 苏和 13739980760
     * @Date 2017/1/23 17:02
     * @return
     * 备品备件求购管理撤回
     */
    public int ManagelistRe(String uid) throws UnsupportedEncodingException {
        return supplychainCreateDao.ManagelistRe(uid);
    }

    /**
     * @author 苏和 13739980760
     * @Date 2017/1/23 17:03
     * @return
     * 备品备件求购管理发布
     */
    public int ManagelistIss(String uid) throws UnsupportedEncodingException {
        return supplychainCreateDao.ManagelistIss(uid);
    }
//    public void ManagelistDell(String uid) throws UnsupportedEncodingException {
//        return supplychainCreateDao.ManagelistDell(uid);
//    }
    public int ManagelistDell(String uid) throws UnsupportedEncodingException {
        supplychainCreateDao.ManagelistDell(uid);

        return 1;
    }


    /**
     * @author 苏和 13739980760
     * @Date 2017/1/23 17:01
     * @return
     * 备品备件求购管理删除
     */
    public SSpareDemand  delete(String demandId){
        SSpareDemand bean = supplychainCreateDao.findById(demandId);//获取能力实体类
        Content contentBean = deleteContentById(bean.getContent().getContentId());
        bean = supplychainCreateDao.delete(bean);// 执行删除
        afterDelete(contentBean);
        return bean;
    }

    public Content deleteContentById(Integer id) {
        Content bean = contentDao.findById(id);
        // 执行监听器
        preDelete(bean);
        // 移除tag
        contentTagMng.removeTags(bean.getTags());
        bean.getTags().clear();
        // 删除评论
        cmsCommentMng.deleteByContentId(id);
        // 删除留言
        cmsConsultMng.deleteByContentId(id);
        //删除附件记录
        fileMng.deleteByContentId(id);
        bean.clear();
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

    /**
     * @author machao
     * @Date 2017/1/7 14:40
     * @return
     * 金牌老师傅推荐、撤销
     */
    public Pagination getJplsftjPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Integer memberId, int pageNo, int pageSize,
                                              Date startDate, Date endDate,  String releaseName, String repairType,String flag,String companyName,String workYear,String addrCity){
        return supplychainCreateDao.getJplsftjList(channelId,siteId,modelId,memberId, pageNo, pageSize,startDate, endDate, releaseName,repairType,flag,companyName, workYear, addrCity);
    }
    /**
     * @author machao
     * @Date 2017/1/7 14:40
     * @return
     * 金牌老师傅推荐、撤销
     */
    public boolean recommendManage(String id,String flag) {
        return supplychainCreateDao.recommendManage(id,flag);
    }
/**
 * @author dongxuecheng
 * @Date 2017/1/6 8:55
 * @return
 * @description备品备件求购发布板
 */
    public Content save(SSpareDemand bean, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b, String demandObjListJsonString) {
        if(charge==null){
            charge= ContentCharge.MODEL_FREE;
        }
         Content newC= saveContent(c, ext, t, channelId, typeId, null,true,user, b);

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
        bean.setSpareDemandObjLists(convertJStringToList(demandObjListJsonString,bean.getDemandId()));
//        bean.setContentId(newC.getContentId().toString());//获取contentid
        supplychainCreateDao.insertsSpareDemandEntity(bean);
        afterSave(c);
        return c;
    }

    /**
     * @author: gao jiangning
     * @desciption 将前台传入的json String转化为需求对象的List
     */
    private List<SSpareDemandObj> convertJStringToList(String sSpareDemandObjs,String demandId){
        JSONObject jsonObj = new JSONObject(sSpareDemandObjs);
        JSONArray jsonArr = (JSONArray) jsonObj.get("demandObjs");
        List<SSpareDemandObj> spareDemandObjList = new ArrayList<SSpareDemandObj>();
        if(jsonArr==null||jsonArr.length()==0){
            return null;
        }
        java.sql.Date createDate = new java.sql.Date(System.currentTimeMillis());
        //遍历传过来的json对象数组，取出值转换后置入对象，对象再置入list
        for(int i = 0; i<jsonArr.length(); i++){
            JSONObject spareDemandObj = jsonArr.getJSONObject(i);
            SSpareDemandObj SdObj = new SSpareDemandObj();

            SdObj.setSpareObjid(UUID.randomUUID().toString().replace("-",""));//唯一标识
            SdObj.setDemandId(demandId);//求购信息ID
            SdObj.setSpareType(spareDemandObj.getString("spareType"));//备件分类
            SdObj.setSpareName(spareDemandObj.getString("spareName"));//备件名称
            SdObj.setSpareCode(spareDemandObj.getString("spareType"));//备件编码(待定)
            SdObj.setRequestNum(spareDemandObj.getDouble("requestNum"));//求购数量
            SdObj.setSpareDesc(spareDemandObj.getString("spareDesc"));//备件描述
            SdObj.setUnit(spareDemandObj.getString("unit"));//计量单位
            SdObj.setExpectPrice(spareDemandObj.getDouble("expectPrice"));//期望单价
            SdObj.setCreaterId(spareDemandObj.getString("spareType"));//创建人
            SdObj.setCreateDt(createDate);//创建时间
            SdObj.setUpdateDt(createDate);//更新时间
            SdObj.setDeliverDt(createDate);//要求交货日期
            SdObj.setDeFlag("1");//逻辑删除
            spareDemandObjList.add(SdObj);
        }
        return spareDemandObjList;
    }

/**
 * @author 苏和 13739980760
 * @Date 2017/1/6 15:05
 * @return
 * 备品备件求购管理
 */
public Pagination getPageForMember(String requestType,Integer channelId, Integer siteId, Integer modelId, Integer memberId,
                                   int pageNo, int pageSize, Date startDate, Date endDate,
                                   String releaseId, String dealType,
                                   String expectPrice, String isUrgency, String requestTheme,String invoiceType,String status) {
    return daoqg.getPageBySelf(requestType,null,memberId, false, false, Content.ContentStatus.all, null,
            siteId, channelId, pageNo, pageSize, startDate, endDate, releaseId,
            dealType,expectPrice,isUrgency,requestTheme,invoiceType,status);
}




    public Pagination getList(String isUrgency, String requestTheme,
                              Integer pageNo, Integer pageSize) throws UnsupportedEncodingException {
        Pagination page = supplychainCreateDao.getPage(isUrgency,requestTheme,pageNo, pageSize);
        return page;
    }




public Pagination getBPB(/*Integer channelId, Integer siteId, Integer modelId,
                                     Integer memberId,*/ int pageNo, int pageSize/*,Date startDate, Date endDate,  String releaseName, String repairTyp*/,
                         String theme/*, String type*/){
        return supplychainCreateDao.getBP(pageNo,pageSize,theme);
    }
    /**
     * @author 苏和 13739980760
     * @Date 2017/1/10 16:56
     * @return
     * 备品备件求购编辑
     */

    public void update(SSpareDemand sSpareDemand, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths,
                       String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs,
                       Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b,
                       String demandObjListJsonString) {
        //更新cms表内容
        Content nc=contentMng.update(c,ext,t,null,null,null,null,attachmentPaths,attachmentNames,attachmentFilenames,picPaths,
                picDescs,null,channelId,typeId,null,charge,null,user,b);
        //更新需求内容
       // sSpareDemand.setsSpareDemandObj(convertJStringToList(demandObjListJsonString,sSpareDemand.getDemandId()));


        List<SSpareDemandObj> sDemandObjList =convertJStringToList(demandObjListJsonString);
        sSpareDemand.setSpareDemandObjLists(sDemandObjList);

        sSpareDemand.setContent(nc);
        Updater<SSpareDemand> updater = new Updater<SSpareDemand>(sSpareDemand);
        supplychainCreateDao.updateByUpdater(updater);
        int i=1;
    }

    /**
     * @author dongxuecheng
     * @Date 2017/1/11 14:41
     * @return
     * @description更新的时候转化json字符串
     */
    private List<SSpareDemandObj> convertJStringToList(String sSpareDemandObjs){
        JSONObject jsonObj = new JSONObject(sSpareDemandObjs);
        JSONArray jsonArr = (JSONArray) jsonObj.get("demandObjs");
        List<SSpareDemandObj> spareDemandObjList = new ArrayList<SSpareDemandObj>();
        if(jsonArr==null||jsonArr.length()==0){
            return null;
        }
        java.sql.Date createDate = new java.sql.Date(System.currentTimeMillis());
        for(int i = 0; i<jsonArr.length(); i++){
            JSONObject spareDemandObj = jsonArr.getJSONObject(i);
            SSpareDemandObj SdObj = new SSpareDemandObj();
            SdObj.setSpareObjid(spareDemandObj.getString("spareObjid"));//主键
            SdObj.setSpareType(spareDemandObj.getString("spareType"));//备件分类
            SdObj.setSpareName(spareDemandObj.getString("spareName"));//备件名称
            SdObj.setSpareCode(spareDemandObj.getString("spareType"));//备件编码(待定)
            SdObj.setRequestNum(spareDemandObj.getDouble("requestNum"));//求购数量
            SdObj.setSpareDesc(spareDemandObj.getString("spareDesc"));//备件描述
            SdObj.setUnit(spareDemandObj.getString("unit"));//计量单位
            SdObj.setExpectPrice(spareDemandObj.getDouble("expectPrice"));//期望单价
            SdObj.setCreaterId(spareDemandObj.getString("spareType"));//创建人
            SdObj.setUpdateDt(createDate);//更新时间
            SdObj.setDeFlag("1");//逻辑删除
            spareDemandObjList.add(SdObj);
        }
        return spareDemandObjList;
    }
    /**
     * @author machao
     * @Date 2017/1/17 10:27
     * @return
     *
     * 自定义标签-维修资源展示
     */
    public List<SRepairRes> getRepairResList( Integer orderBy, Integer listType) {
        return supplychainCreateDao.getRepairResList( orderBy, listType);
    }
    public List<SRepairRes> getRepairResList( Integer count, Integer orderBy, Integer listType) {
        return supplychainCreateDao.getRepairResList(count,orderBy, listType);
    }


    /**
     * @author machao
     * @Date 2017/1/17 10:55
     * @return
     * 自定义标签-备品备件求购
     */
    public List<SSpareDemand> getSpareDemandResList(Integer count, Integer orderBy, Integer listType,String requestType){
        return supplychainCreateDao.getSpareDemandResList(count, orderBy, listType,requestType);
    }
    /**
     * @author gengwenlong
     * @Date 2017/2/18 11:11
     * @return
     * 自定义标签-备品备件求购即将过期
     */
    public List<SSpareDemand> getSpareDemandResLists(Integer count, Integer orderBy, Integer listType){
        return supplychainCreateDao.getSpareDemandResLists(count, orderBy, listType);
    }
    /**
     * @author machao
     * @Date 2017/2/9 14:00
     * @return
     * 自定义标签-备品备件
     */
    public List<SSpare> getSpareList(Integer count, Integer orderBy, Integer listType,String spareType){
        return supplychainCreateDao.getSpareList(count, orderBy, listType, spareType);
    }
    /**
     * @author machao
     * @Date 2017/2/19 14:24
     * @return
     * 企业信息下拉框
     */
    public List<SCompany> getCompanyLists(Integer count, String orderBy, Integer listType){
        return supplychainCreateDao.getCompanyLists(count, orderBy, listType);
    }
    /**
     * @author machao
     * @Date 2017/1/17 10:59
     * @return getRepairDemandList
     * 自定义标签-维修需求展示
     */
    public List<SRepairDemand> getRepairDemandList(Integer count, Integer orderBy, Integer listType,String demandType){
        return supplychainCreateDao.getRepairDemandList(count, orderBy, listType,demandType);
    }
    /**
     * @author gengwenlong
     * @Date 2017/2/17 10:44
     * @return
     * 自定义标签-维修需求即将过期展示
     */
    public List<SRepairDemand> getRepairDemandLists(Integer count, Integer orderBy, Integer listType){
        return supplychainCreateDao.getRepairDemandLists(count, orderBy, listType);
    }

    @Resource
    private HotDemandDao yhrdao;
    @Resource
    private ContentMng contentMng;

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
    private SupplychainCreateDao daoqg;
    @Resource
    private ContentTagMng contentTagMng;
    @Resource
    private CmsCommentMng cmsCommentMng;
    @Resource
    private CmsFileMng fileMng;
    @Resource
    private CmsFileMng cmsConsultMng;



}
