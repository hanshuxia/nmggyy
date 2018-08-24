package com.anchorcms.icloud.service.synergy.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.assist.CmsCommentMng;
import com.anchorcms.cms.service.assist.CmsFileMng;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.dao.CmsUserDao;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.synergy.SCompanyDeviceDao;
import com.anchorcms.icloud.dao.synergy.SCompanyDiplomaDao;
import com.anchorcms.icloud.dao.synergy.SynergyCompanyDao;
import com.anchorcms.icloud.model.synergy.SCompany;
import com.anchorcms.icloud.model.synergy.SCompanyAptitude;
import com.anchorcms.icloud.model.synergy.SCompanyDevice;
import com.anchorcms.icloud.model.synergy.SCompanyDiploma;
import com.anchorcms.icloud.service.synergy.SynergyCompanyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.*;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * Created by ly on 2016/12/27.
 */
@Service
@Transactional
public class SynergyCompanyServiceImpl implements SynergyCompanyService {

    /**
     *@Author ly
     *@Date 2016/12/27 8:56
     *@Desc 保存企业基本信息
     **/
    public SCompany save(SCompany bean, Integer channelId, Integer modelId, CmsUser user, Short charge, boolean b) {

        java.sql.Date nowDate = new Date(System.currentTimeMillis());
        if(user.getCompany() == null){//账号没有企业信息走新增方法
            String uuid = StringUtils.remove(UUID.randomUUID().toString(), '-');
            bean.setCompanyId(uuid);
            bean.setCreateId(user.getUserId().toString());
            bean.setCreateDt(nowDate);
            bean.setUpdateDt(nowDate);
            dao.save(bean);
            user.setCompany(bean);
            cmsUserDao.update(user);//更新用户表
        } else{//账号已有企业信息走更新方法
            SCompany company = user.getCompany();
            company.setCompanyCode(bean.getCompanyCode());
            company.setCompanyName(bean.getCompanyName());
            company.setCompanyType(bean.getCompanyType());
            company.setCompanyScale(bean.getCompanyScale());
            company.setServerType(bean.getServerType());
            company.setIndustryType(bean.getIndustryType());
            company.setOperateType(bean.getOperateType());
            company.setMainProduct(bean.getMainProduct());
            company.setDeviceCount(bean.getDeviceCount());
            company.setProductCount(bean.getProductCount());
            company.setRegisterDt(bean.getRegisterDt());
            company.setMobile(bean.getMobile());
            company.setAddrStreet(bean.getAddrStreet());
            company.setAddrProvince(bean.getAddrProvince());
            company.setAddrCity(bean.getAddrCity());
            company.setAddrCounty(bean.getAddrCounty());
            company.setPostCode(bean.getPostCode());
            company.setUpdateDt(nowDate);
            dao.update(company);
        }
        return null;
    }

    /**
     *@Author ly
     *@Date 2016/12/29 13:44
     *@Desc （舍弃）
     **/
    public SCompanyDiploma add(Integer channelId, Integer modelId, CmsUser user) {

        List<SCompanyDiploma> diplomaList = new ArrayList();
        SCompanyDiploma diploma = new SCompanyDiploma();
        diploma.setCompanyId(user.getCompany().getCompanyId());
        diplomaList.add(diploma);

       /* if(user.getCompany() == null){
            return null;
        }else {
            SCompany company = user.getCompany();
            company.setDiplomaList(diplomaList);
            dao.update(company);
        }
            dao.save(bean);*/
        return null;
    }

    /**
     *@Author ly
     *@Date 2016/12/27 16:56
     *@Desc 企业荣誉列表
     **/
    public Pagination getDiplomaPage(Integer siteId, CmsUser user, int cpn, int pageSize) {
        return companyDiplomaDao.getPage(siteId, user, cpn, pageSize);
    }

    /**
     *@Author ly
     *@Date 2016/12/29 13:42
     *@Desc 保存企业荣誉资质
     **/
    public SCompanyDiploma saveDiploma(Integer channelId, Integer modelId, CmsUser user, SCompanyDiploma diploma) {

        java.sql.Date nowDate = new Date(System.currentTimeMillis());
        diploma.setCompanyId(user.getCompany().getCompanyId());//企业id
        diploma.setCreateDt(nowDate);//创建时间
        diploma.setCreateId(user.getUserId().toString());//创建人
        diploma.setUpdateDt(nowDate);//更新时间
        companyDiplomaDao.save(diploma);
        return diploma;
    }

    /**
     *@Author ly
     *@Date 2016/12/29 17:48
     *@Desc 根据id查找企业荣誉
     **/
    public SCompanyDiploma findDiplomaById(Integer id) {
        return companyDiplomaDao.findById(id);
    }

    public SCompanyAptitude findAptitudeByCompanyId(String id) {
        return companyDiplomaDao.findByCompanyId(id);
    }
    /**
     *@Author ly
     *@Date 2016/12/29 14:53
     *@Desc 更新企业荣誉
     **/
    public SCompanyDiploma updateDiploma(Integer channelId, Integer modelId, CmsUser user, Integer diplomaId,
                                         String diplomaType, String issuser, Date operationDt,
                                         Date deadlineDt, String diplomaPic,String diplomaDiscribe) {
        java.sql.Date nowDate = new Date(System.currentTimeMillis());
        SCompanyDiploma diploma = companyDiplomaDao.findById(diplomaId);
        diploma.setDiplomaType(diplomaType);
        diploma.setDiplomaPic(diplomaPic);
        diploma.setDiplomaDiscribe(diplomaDiscribe);
        diploma.setIssuser(issuser);
        diploma.setOperationDt(operationDt);
        diploma.setDeadlineDt(deadlineDt);
        diploma.setUpdateDt(nowDate);//更新时间
        companyDiplomaDao.update(diploma);
        return diploma;
    }

    /**
     *@Author ly
     *@Date 2016/12/30 9:30
     *@Desc 删除企业荣誉
     **/
    public SCompanyDiploma deleteDiplomaById(Integer id) {
        companyDiplomaDao.deleteById(id);
        return null;
    }

    /**
     *@Author ly
     *@Date 2016/12/30 14:09
     *@Desc 保存企业设备信息
     **/
    public Content saveDevice(SCompanyDevice bean, Content c, ContentExt ext, ContentTxt t,
                              String[] picPaths, String[] picDescs, Integer channelId,
                              Integer typeId, CmsUser user, Short charge, boolean forMember) {

        if(charge==null){
            charge= ContentCharge.MODEL_FREE;
        }
        saveContent(c, ext, t, channelId, typeId, null,true,user, forMember);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。
        Channel channel=channelMng.findById(channelId);
        c.addToChannels(channel);
        // 保存图片集
        if (picPaths != null && picPaths.length > 0) {
            for (int i = 0, len = picPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(picPaths[i])) {
                    c.addToPictures(picPaths[i], picDescs[i]);
                }
            }
        }
        bean.setContent(c);
        companyDeviceDao.save(bean);
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

    /**
     *@Author ly
     *@Date 2016/12/30 17:05
     *@Desc 获取企业信息列表
     **/
    public Pagination getDevicePage(Integer siteId, CmsUser user, int cpn, int pageSize) {
        return companyDeviceDao.getPage(siteId, user, cpn, pageSize);
    }

    /**
     *@Author ly
     *@Date 2016/12/30 17:05
     *@Desc 根据id查找企业设备
     **/
    public SCompanyDevice findDeviceById(Integer id) {
        return companyDeviceDao.findById(id);
    }

    /**
     *@Author ly
     *@Date 2017/1/3 13:00
     *@Desc 删除企业设备
     **/
    public SCompanyDevice deleteDeviceById(Integer id) {
        SCompanyDevice bean = companyDeviceDao.findById(id);//获取企业设备实体类
        Content contentBean = deleteContentById(bean.getContent().getContentId());
        companyDeviceDao.deleteById(id);
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
        //删除附件记录
        fileMng.deleteByContentId(id);
        bean.clear();
        bean = contentDao.deleteById(id);
        // 执行监听器

        return bean;
    }
    /**
     *@Author ly
     *@Date 2017/1/3 14:56
     *@Desc 更新企业设备信息
     **/
    public SCompanyDevice updateDevice(Integer id, SCompanyDevice bean, String detailDesc,
                                       String[] picPaths, String[] picDescs, Integer channelId,
                                       CmsUser user, Short charge, boolean forMember) {

        SCompanyDevice device = findDeviceById(id);
        device.setDeviceType(bean.getDeviceType());
        device.setDeviceName(bean.getDeviceName());
        device.setDeviceCode(bean.getDeviceCode());
        device.setUnit(bean.getUnit());
        device.setExpectPrice(bean.getExpectPrice());
        device.setUpdateDt(bean.getUpdateDt());
        device.setContact(bean.getContact());
        device.setMobile(bean.getMobile());
        Content c = device.getContent();
        ContentExt ext = c.getContentExt();
        //富文本
        ContentTxt txt = c.getContentTxt();
        txt.setTxt(detailDesc);
        // 执行监听器
        List<Map<String, Object>> mapList = preChange(c);
        //更新content
        c = updateContent(c, ext, txt, picPaths, picDescs, channelId, charge, user, forMember);
        //更新设备表
        companyDeviceDao.update(device);
        // 执行监听器
        afterChange(c, mapList);

        return device;
    }

    /**
     *@Author ly
     *@Date 2017/1/3 17:40
     *@Desc 更新企业设备content
     **/
    public Content updateContent(Content bean, ContentExt ext, ContentTxt txt, String[] picPaths,
                          String[] picDescs, Integer channelId, Short charge, CmsUser user,
                          boolean forMember) {

        // 更新主表
        Updater<Content> updater = new Updater<Content>(bean);
        bean = contentDao.updateByUpdater(updater);

        // 更新扩展表
        contentExtMng.update(ext);
        // 更新文本表
        contentTxtMng.update(txt, bean);
        // 更新图片集
        bean.getPictures().clear();
        if (picPaths != null && picPaths.length > 0) {
            for (int i = 0, len = picPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(picPaths[i])) {
                    bean.addToPictures(picPaths[i], picDescs[i]);
                }
            }
        }

        return bean;
    }

    /**
     *@Author ly
     *@Date 2017/1/3 17:15
     *@Desc 保存企业详细信息
     **/
    public Content saveDetail(Content c, ContentExt ext, ContentTxt t, String[] picPaths,
                              String[] picDescs, Integer channelId, Integer typeId, CmsUser user,
                              Short charge, boolean forMember) {

        if(charge==null){
            charge= ContentCharge.MODEL_FREE;
        }
        saveContent(c, ext, t, channelId, typeId, null,true,user, forMember);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。
        Channel channel=channelMng.findById(channelId);
        c.addToChannels(channel);
        // 保存图片集
        if (picPaths != null && picPaths.length > 0) {
            for (int i = 0, len = picPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(picPaths[i])) {
                    c.addToPictures(picPaths[i], picDescs[i]);
                }
            }
        }
        SCompany company = user.getCompany();
        company.setContent(c);
        dao.update(company);
        afterSave(c);
        return c;
    }

    /**
     *@Author ly
     *@Date 2017/1/3 18:09
     *@Desc 更新企业详细信息
     **/
    public Content updateDetail(String detailDesc, String[] picPaths, String[] picDescs, Integer channelId, CmsUser user, Short charge, boolean forMember) {
        Content c = user.getCompany().getContent();
        ContentExt ext = c.getContentExt();
        //富文本
        ContentTxt txt = c.getContentTxt();
        txt.setTxt(detailDesc);
        // 执行监听器
        List<Map<String, Object>> mapList = preChange(c);
        //更新content
        c = updateContent(c, ext, txt, picPaths, picDescs, channelId, charge, user, forMember);
        // 执行监听器
        afterChange(c, mapList);
        return null;
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

    private List<Map<String, Object>> preChange(Content content) {
        if (listenerList != null) {
            int len = listenerList.size();
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>(
                    len);
            for (ContentListener listener : listenerList) {
                list.add(listener.preChange(content));
            }
            return list;
        } else {
            return null;
        }
    }

    private void afterChange(Content content, List<Map<String, Object>> mapList) {
        if (listenerList != null) {
            Assert.notNull(mapList);
            Assert.isTrue(mapList.size() == listenerList.size());
            int len = listenerList.size();
            ContentListener listener;
            for (int i = 0; i < len; i++) {
                listener = listenerList.get(i);
                listener.afterChange(content, mapList.get(i));
            }
        }
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

    public List<SCompanyDiploma> getDiplomaList(Integer count, Integer orderBy, Integer listType) {
        return companyDiplomaDao.getDiplomaList(count, orderBy, listType);
    }

    public List<SCompanyDevice> getDeviceList(Integer count, Integer orderBy, Integer listType) {
        return companyDeviceDao.getDeviceList(count, orderBy, listType);
    }

    public Content saveAptitude(SCompanyAptitude sCompanyAptitude, Content c, ContentExt ext, ContentTxt t, String[] picPaths, String[] picDescs,  String[] picPaths1, String[] picDescs1,
                                String[] picPaths2, String[] picDescs2, String[] picPaths3, String[] picDescs3, String[] picPaths4, String[] picDescs4,Integer channelId, Integer typeId, CmsUser user, Short charge, boolean forMember) {
        if (charge == null){
            charge = ContentCharge.MODEL_FREE;
        }
//        saveContent(c, ext, t, channelId, typeId, null, true, user, forMember);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。
//        Channel channel = channelMng.findById(channelId);
//        c.addToChannels(channel);
//        SCompany company = user.getCompany();
//        company.setContent(c);
        java.sql.Date nowDate = new Date(System.currentTimeMillis());
        if(user.getCompany().getAptitude().size() == 0){ // 账号没有资质信息走新增方法
            String uuid = StringUtils.remove(UUID.randomUUID().toString(), '-');
            sCompanyAptitude.setAptitudeId(uuid);
//            sCompanyAptitude.setContent(c);
            sCompanyAptitude.setCreaterId(user.getUserId().toString());
            sCompanyAptitude.setCreateDt(nowDate);
            sCompanyAptitude.setUpdateDt(nowDate);
            dao.saveAptitude(sCompanyAptitude);
            cmsUserDao.update(user); // 更新用户表
        } else{ // 账号已有资质信息走更新方法
            dao.updateAptitude(sCompanyAptitude);
        }
//        afterSave(c);
        return c;
    }
    public List<SCompany> findSCompanyByName(String companyName){
        return dao.findSCompanyByName(companyName);
    }

    @Resource
    private CmsUserDao cmsUserDao;
    @Resource
    private SynergyCompanyDao dao;
    @Resource
    private SCompanyDiplomaDao companyDiplomaDao;
    @Resource
    private SCompanyDeviceDao companyDeviceDao;
    @Resource
    private List<ContentListener> listenerList;
    @Resource
    private ContentDao contentDao;
    @Resource
    private ContentTxtMng contentTxtMng;
    @Resource
    private ContentExtMng contentExtMng;
    @Resource
    private ContentCountMng contentCountMng;
    @Resource
    private ContentCheckMng contentCheckMng;
    @Resource
    private ChannelMng channelMng;
    @Resource
    private ContentTypeMng contentTypeMng;
    @Resource
    private ContentTagMng contentTagMng;
    @Resource
    private CmsCommentMng cmsCommentMng;
    @Resource
    private CmsFileMng fileMng;
}
