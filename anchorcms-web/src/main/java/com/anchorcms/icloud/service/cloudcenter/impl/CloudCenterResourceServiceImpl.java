package com.anchorcms.icloud.service.cloudcenter.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentCheck;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.cms.service.assist.CmsCommentMng;
import com.anchorcms.cms.service.assist.CmsConsultMng;
import com.anchorcms.cms.service.assist.CmsFileMng;
import com.anchorcms.cms.service.main.ContentExtMng;
import com.anchorcms.cms.service.main.ContentListener;
import com.anchorcms.cms.service.main.ContentTagMng;
import com.anchorcms.cms.service.main.ContentTxtMng;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.common.utils.CmsUtils;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.cloudcenter.CloudCenterResourceDao;
import com.anchorcms.icloud.model.cloudcenter.SIcloudManage;
import com.anchorcms.icloud.service.cloudcenter.CloudCenterResourceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author 姜舒中
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/7 0007
 * @Desc
 */
@Service
@Transactional
public class CloudCenterResourceServiceImpl implements CloudCenterResourceService {
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Integer memberId, int pageNo, int pageSize, Date startDt, Date endDt, Integer managerId, String resourceType, String state, String addrCity){

        return dao.getPageBySelf(channelId,siteId,modelId,UserId,memberId,pageNo,pageSize,startDt,endDt,managerId,resourceType,state,addrCity);
    }

    public SIcloudManage delete(Integer managerId){
        SIcloudManage bean = dao.findById(managerId);//获取能力实体类
        Content contentBean = deleteContentById(bean.getContent().getContentId());
        bean = dao.delete(bean);// 执行删除
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
     * @Author iangshuzhong
     * @param managerId 文章id
     * @Date 2016/1/7
     * @Desc 云资源管理详情
     */
    public SIcloudManage byManagerId(Integer managerId) {
        return dao.findById(managerId);
    }
    /**
     * @Author jsz
     * 更新云资源信息
     * @Date 2016/1/9
     */
    public SIcloudManage update(
            HttpServletRequest request, HttpServletResponse response, ModelMap model,
            Integer managerId,
            String release_people,
            String center_name,
            String resourceName,//云资源名称
            String resourceType,
            String specVersion,
            String parameter,
            String rentPrice,
            Double price,
            String unit,
            String addrCity,
            String contact,//联系人
            String mobile,//联系电话
            String email,//邮箱
            //cms表相关参数
            Integer modelId, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames,String detailDesc,  String[] picPaths, String[] picDescs, Integer channelId, Short charge, String nextUrl){
        CmsSite site = CmsUtils.getSite(request);
        CmsUser user = CmsUtils.getUser(request);
        SIcloudManage sd = byManagerId(managerId);
        sd.setRelease_people(release_people);
        sd.setCenter_name(center_name);
        sd.setResourceName(resourceName);//云资源名称
        sd.setResourceType(resourceType);
        sd.setSpecVersion(specVersion);
        sd.setParameter(parameter);
        sd.setRentPrice(rentPrice);
        sd.setUnit(unit);
        sd.setPrice(price);
        sd.setAddrCity(addrCity);
        sd.setContact(contact);
        sd.setMobile(mobile);
        sd.setEmail(email);
        sd.setState("0");
        sd.setReleaseDt(new java.sql.Date(System.currentTimeMillis()));
        //默认值set
        sd.setDeFlag("1");
        sd.setCreateDt(new java.sql.Date(System.currentTimeMillis()));
        sd.setCreaterId(user.getUserId()+"");

        Content c = sd.getContent();
        ContentExt ext = c.getContentExt();
        ext.setTitle(resourceName);
        //富文本
        ContentTxt txt = c.getContentTxt();
        txt.setTxt(detailDesc);
        // 执行监听器
        List<Map<String, Object>> mapList = preChange(c);
        //更新content
        c = updateContent(c, ext, txt, attachmentPaths, attachmentNames, attachmentFilenames,
                picPaths, picDescs);
        dao.update(sd);
        // 执行监听器
        afterChange(c, mapList);
        return null;

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

    public Content updateContent(Content bean, ContentExt ext, ContentTxt txt,
                                 String[] attachmentPaths, String[] attachmentNames,
                                 String[] attachmentFilenames, String[] picPaths,
                                 String[] picDescs) {

        // 更新主表
        Updater<Content> updater = new Updater<Content>(bean);
        bean = contentDao.updateByUpdater(updater);

        // 更新扩展表
        contentExtMng.update(ext);
        // 更新文本表
        if(txt !=null) {
            contentTxtMng.update(txt, bean);
        }
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
     * @author jsz
     * @date 2017/1/9 13:47
     * @desc 自定义标签——能力列表获取
     **/
    public List<SIcloudManage> getList(Integer count, Integer orderBy, Integer listType,Integer areaType,Integer state) {
        return dao.getList(count, orderBy, listType,areaType,state);
    }

    /**
     * @param id
     * @param status
     * @param channelId
     * @param user
     * @param charge
     * @param b
     * @Author wanjinyou
     * @Date 2016/1/20
     * @Desc 云资源管理下架
     */
    public SIcloudManage updateState(Integer id, String status, Integer channelId, CmsUser user, Short charge, boolean b) {
        SIcloudManage manage = byManagerId(id);
        Content content = manage.getContent();
        if (status != null) {
            // 执行监听器
            List<Map<String, Object>> mapList = preChange(content);
            manage.setState(status);
            if (Integer.parseInt(status) == 1){//已发布时将content状态改为已审核
                content.setStatus(ContentCheck.CHECKED);
            }else {//其他未审核状态
                content.setStatus(ContentCheck.CONTRIBUTE);
            }
            // 更新content表
            Updater<Content> updater = new Updater<Content>(content);
            content = contentDao.updateByUpdater(updater);
            //更新云资源表
            dao.update(manage);
            // 执行监听器
            afterChange(content, mapList);
        }
        return manage;
    }

    /**
     * @param id
     * @Author wanjinyou
     * @Date 2016/1/20
     * @Desc 云资源管理删除 【用户公司一条需求的明细】
     */
    public SIcloudManage deleteById(Integer id) {
       SIcloudManage bean = byManagerId(id);
        Content contentBean = deleteContentById(bean.getContent().getContentId());
        dao.deleteById(id);
        afterDelete(contentBean);
        return bean;
    }

    /**
     * 修改数据状态（发布资源功能）
     *
     * @param managerId
     */
    public void updateState(Integer managerId) {
        SIcloudManage sd = byManagerId(managerId);
        sd.setState("1");
        dao.update(sd);
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

    /**
     * @Author 闫浩芮
     * 管理员批量删除
     * @Date 2017/2/22 0022 19:36
     */
    public void deleteMany(String ids) {
        if(',' == (ids.charAt(ids.length()-1))){
            ids = ids.substring(0,ids.length()-1);
            String[] idarr=ids.split(",");
            for(int i =0;i<idarr.length;i++){
                deleteById(Integer.parseInt(idarr[i]));
            }
        }
    }


    @Resource
    private List<ContentListener> listenerList;
    @Resource
    private CloudCenterResourceDao dao;
    @Resource
    private ContentDao contentDao;
    @Resource
    private ContentTxtMng contentTxtMng;
    @Resource
    private ContentExtMng contentExtMng;
    @Resource
    private ContentTagMng contentTagMng;
    @Resource
    private CmsCommentMng cmsCommentMng;
    @Resource
    private CmsConsultMng cmsConsultMng;
    @Resource
    private CmsFileMng fileMng;
}
