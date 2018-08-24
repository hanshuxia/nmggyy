package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.hibernate.Updater;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.supplychain.MasterManagerDao;
import com.anchorcms.icloud.dao.supplychain.SSpareDao;
import com.anchorcms.icloud.dao.supplychain.SSpareDetailDao;
import com.anchorcms.icloud.model.supplychain.SSpare;
import com.anchorcms.icloud.service.supplychain.SSpareService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;


/**
 * @Author zhouyq
 * @Date 2016/12/20
 * @Desc 备品备件服务实现类
 */
@Service
@Transactional
public class SSpareServiceImpl implements SSpareService {
    @Resource
    private SSpareDao dao;

    /**
     * @Author zhouyq
     * @Date 2016/12/20
     * @param spareType, spareName, spareDesc, companyType, addrCity, pageNo, pageSize
     * @return
     * @Desc 获取备品备件分页后信息
     */
    public Pagination getList(String spareType, String spareName, String spareDesc, String companyType, String addrCity, String addrProvince,
                              Integer pageNo, Integer pageSize) {
        Pagination page = dao.getPage(spareType, spareName, spareDesc, companyType,
                addrCity,addrProvince, pageNo, pageSize);
        return page;
    }
    public Pagination getSSpareStorageList(String spareId, Integer pageNo, Integer pageSize) {
        Pagination page = dao.getSSpareStorageList(spareId, pageNo, pageSize);
        return page;
    }
    /*
    * 刘鹏
    * 备品备件信息上传
    * */
    public Content saveSSpare(SSpare sse, Content c, ContentExt ext, ContentTxt t,
                              Integer channelId, Integer typeId, CmsUser user, boolean b,String[] attachmentPaths,
                              String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs){
        Content bean= saveContent(c, ext, t, channelId, typeId, null, true, user, b);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。
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
        sse.setContent(c);
        sse.setContentId(bean.getContentId().toString());////给资源表（s_repair_res）附上contentid
        Channel channel = channelMng.findById(channelId);
        c.addToChannels(channel);
        dao.save(sse);
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
     * @author李洪波
     * @Desc 根据创建时间，备品分类，来源和所属企业来分类并分页
     * @return 备品备件的分页列表
     */
    public Pagination findByTimeCateSourCompany(Date startDate, Date endDate, String publicType, String status, String spareType, String source, String companyId, Integer pageNo, Integer pageSize, Integer userId, String flag) {
        Pagination pp=dao.findByTimeCateSourCompany(startDate, endDate, publicType, status, spareType, source, companyId,pageNo,pageSize, userId,flag);
        return pp;
    }

    /**
     * @Author李洪波
     * @param id
     * @param publicType
     * @Desc根据备品备件IDs批量修改发布状态
     */
    public void update(String id, String publicType) {
        spareDao.updateSSpare(id,publicType);
    }

    /**
     * @Author李洪波
     * @param id
     * @param status
     * @Desc修改备品备件发布状态
     */
    public void updateSpareStatus(String id, String status, Date date,String flag) {

            spareDao.updateSpareStatus(id,"2",date, flag);

    }

    /**
     * @author李洪波
     * @Desc根据ID删除备品备件
     * @param id
     * @param flag
     */
    public void delete(String id, String flag) {
        SSpare bean= sSpareMng.findById(id);
        Content contentBean = deleteContentById(bean.getContent().getContentId());
        bean=spareDao.delete(bean);
        afterDelete(contentBean);
    }

    /**
     * @author李洪波
     * @Desc备品备件编辑
     * @param spare
     * @param c
     * @param ext
     * @param t
     * @param attachmentPaths
     * @param attachmentNames
     * @param attachmentFilenames
     * @param picPaths
     * @param picDescs
     * @param channelId
     * @param typeId
     * @param user
     * @param charge
     * @param b
     */
    public void edit(SSpare spare, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs, Integer channelId, Integer typeId, CmsUser user, Short charge, boolean b) {
        //更新cms表内容
        Content nc=contentMng.update(c,ext,t,null,null,null,null,attachmentPaths,attachmentNames,attachmentFilenames,picPaths,
                picDescs,null,channelId,typeId,null,charge,null,user,b);
        spare.setContent(nc);
        Updater<SSpare> updater = new Updater<SSpare>(spare);
        spareDao.updateByUpdater(updater);

    }

    /**
     * @Author李洪波
     * @Desc删除content中对应的数据
     * @param id
     * @return
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
     * @Author李洪波
     * @Desc删除content前的监听器
     * @param content
     */
    private void preDelete(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.preDelete(content);
            }
        }
    }

    /**
     * @Author李洪波
     * @Desc删除content后的监听器
     * @param content
     */
    private void afterDelete(Content content) {
        if (listenerList != null) {
            for (ContentListener listener : listenerList) {
                listener.afterDelete(content);
            }
        }
    }


    @Resource
    private MasterManagerDao masterManagerDao ;
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
    private ContentMng contentMng;
    @Resource
    private SSpareDao spareDao;
    @Resource
    private SSpareDetailDao sSpareMng;
}
