package com.anchorcms.icloud.service.common.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.common.CfcaDao;
import com.anchorcms.icloud.dao.synergy.HotDemandDao;
import com.anchorcms.icloud.dao.synergy.SDemandCreateDao;
import com.anchorcms.icloud.model.common.SCfcaApply;
import com.anchorcms.icloud.model.common.SCfcaPay;
import com.anchorcms.icloud.service.common.CfcaService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * @author machao
 * @Date 2017/3/30 14:45
 * @return cfca审核
 */
@Service
@Transactional
public class CfcaServiceImpl implements CfcaService {


    /**
     * @Author zhouyq
     * @Date 2017/03/30
     * @param
     * @return
     * @Desc 获取电子签章分页后信息
     */
    public Pagination getList(String companyName, String status , Integer pageNo, Integer pageSize) {
        Pagination page = cfcaDao.getPage(companyName, status, pageNo, pageSize);
        return page;
    }

    /**
     * @Author zhouyq
     * @Date 2017/03/31
     * @param
     * @return
     * @Desc 获取电子签章明细信息
     */
    public SCfcaApply getCfcaEntity(String id) {
        return cfcaDao.getCfcaEntity(id);
    }

    public List getApplyList(CmsUser user) {
        return cfcaDao.getList(user);
    }

    public Pagination getApplyPage(CmsUser user, int cpn, int i) {
        return cfcaDao.getPage(user, cpn, i);
    }
    /**
     * @author ldong
     * @Date 2017/3/30 16:18
 * @return 更改申请数据的状态    撤回  提交  收货
     */
    public void editState(String state, int id) {
        SCfcaApply bean = cfcaDao.findById(id);
        bean.setState(state);
        cfcaDao.updateBean(bean);
    }

    public Content save(SCfcaPay sd, Content c, ContentExt ext, ContentTxt t, String[] attachmentPaths, String[] attachmentNames, String channelId, Integer typeId, CmsUser user, Short charge, boolean b, Integer id) {
        charge = ContentCharge.MODEL_FREE;

        saveContent(c, ext, t, 103, typeId, null, true, user, b);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。
        Channel channel = channelMng.findById(103);
        c.addToChannels(channel);
        // 保存附件
        if (attachmentPaths != null && attachmentPaths.length > 0) {
            for (int i = 0, len = attachmentPaths.length; i < len; i++) {
                if (!StringUtils.isBlank(attachmentPaths[i])) {
                    c.addToAttachmemts(attachmentPaths[i],
                            attachmentNames[i], attachmentNames[i]);
                }
            }
        }

        sd.setContent(c);
        cfcaDao.savePay(sd);
        SCfcaApply bean = cfcaDao.findById(id);
        bean.setSCfcaPay(sd);
        cfcaDao.updateBean(bean);
        afterSave(c);
        return c;
    }

    /**
     * @author zhouyq
     * @Date 2017/4/5
     * @return 判断是否可以进行签章
     */
    public SCfcaApply getSignFlag(String companyId){
        SCfcaApply sCfcaApply = cfcaDao.getSignFlag(companyId);
        return sCfcaApply;
    }

    /**
     * @author ldong
     * @Date 2017/3/31
     * @return 更改申请数据的状态    通过
     */
    public void setPass(int applyId, String signNo, String signPwd) {
        SCfcaApply bean = cfcaDao.findById(applyId);
        bean.setSignNo(signNo);
        bean.setSignPwd(signPwd);
        Date nowdate = new Date (System.currentTimeMillis());//获取当前时间
        bean.setStartDt(nowdate);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(nowdate);
        rightNow.add(Calendar.YEAR,Integer.valueOf(bean.getCreditCode()));//日期加年
        Date deadlinedate=rightNow.getTime();
        bean.setDeadlineDt(deadlinedate);
        bean.setState("4");
        cfcaDao.updateBean(bean);
    }

    public void savePayResult(int id, String remark,
                              String orderNo,
                              String accountNo,
                              String filPath[],
                              String attachmentNames[],
                              Content c) {
        SCfcaApply bean =  cfcaDao.findById(id);
        SCfcaPay po = new SCfcaPay();
        po.setPolicyId(Math.abs(UUID.randomUUID().hashCode()));
        if (filPath != null && filPath.length > 0) {
            for (int i = 0, len = filPath.length; i < len; i++) {
                if (!StringUtils.isBlank(filPath[i])) {
                    c.addToAttachmemts(filPath[i],
                            attachmentNames[i], filPath[i]);
                }
            }
        }
        po.setPolicyLevel(remark);
        po.setPolicyNumber(orderNo);
        po.setPolicyName(accountNo);
        contentDao.save(c);
        po.setContent(c);
        cfcaDao.savePay(po);
        bean.setSCfcaPay(po);
        this.cfcaDao.updateBean(bean);
    }
    /**
     * @author machao
     * @Date 2017/3/31 16:41
     * @return
     * 签章申请保存
     */
    public Content cfcaSealEdit(SCfcaApply sCfcaApply, Content c, ContentExt ext, ContentTxt t,
                                 Integer channelId, Integer typeId, CmsUser user, boolean b, String[] attachmentPaths, String[] attachmentNames, String[] attachmentFilenames, String[] picPaths, String[] picDescs){

        cfcaDao.insert(sCfcaApply);
//        afterSave(c);
        return c;
    }
    public SCfcaApply cfcaSealSave(SCfcaApply sCfcaApply){
        return  cfcaDao.insert(sCfcaApply);
    }


    @Resource
    private CfcaDao cfcaDao;
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


    public void modifyCfcaStateById(String applyId, String state, Date releaseDt, String backReason){
        SCfcaApply sCfcaApply = cfcaDao.getCfcaEntity(applyId);
        sCfcaApply.setBackReason(backReason);
        cfcaDao.updateBean(sCfcaApply);
        cfcaDao.modifyCfcaStateById(applyId, state,releaseDt,backReason);
    }

}
