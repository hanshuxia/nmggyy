package com.anchorcms.icloud.service.supplychain.impl;

import com.anchorcms.cms.dao.main.ContentDao;
import com.anchorcms.cms.model.main.*;
import com.anchorcms.cms.service.main.*;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsSite;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.supplychain.SRepairQuoteDao;
import com.anchorcms.icloud.model.supplychain.SRepairQuote;
import com.anchorcms.icloud.service.supplychain.SRepairQuoteService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static com.anchorcms.cms.model.main.ContentCheck.DRAFT;

/**
 * Created by hansx on 2016/12/27.
 * 维修资源报价信息业务实现类
 */
@Service
@Transactional
public class SRepairQuoteServiceImpl implements SRepairQuoteService {

   /**
    * @author hansx
    * @Date 2017/1/4 0004 下午 4:06
    * @return
    * 根据id获取数据
    */
    public SRepairQuote findById(String id) {
        return sRepairQuoteDao.findById(id);
    }

 /**
  * @author hansx
  * @Date 2017/1/4 0004 下午 4:06
  * @return
  * 保存报价信息
  */
    public Content save(SRepairQuote SRepairQuote, Content c, ContentExt ext, ContentTxt t, Integer channelId, Integer typeId, CmsUser user, boolean forMember) {

        saveContent(c, ext, t, channelId, typeId, null,true,user, forMember);
        // 主栏目也作为副栏目一并保存，方便查询，提高效率。
        Channel channel=channelMng.findById(channelId);
        c.addToChannels(channel);
        //CMS内容表主键id
        SRepairQuote.setContentId(c.getContentId().toString());
        //创建人id
        SRepairQuote.setCreaterId(user.getUserId().toString());
        //创建时间
        SRepairQuote.setCreateDt((java.sql.Date) new Date());
        this.sRepairQuoteDao.save(SRepairQuote);
        afterSave(c);
        return c;
    }

    /**
     * @author hansx
     * @Date 2017/1/4 0004 下午 4:06
     * @return
     * 获取报价列表
     */
    public List<SRepairQuote> getList() {
        return sRepairQuoteDao.getList();
    }

    /**
     * @author hansx
     * @Date 2017/1/4 0004 下午 2:03
     * @return
     * 待报价管理列表
     */
    public Pagination getInquiryListForMember(String inquiryTheme, Date startDate, Date endDate, String companyId,
                                       Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize) {
        return sRepairQuoteDao.getInquiryListForMember(inquiryTheme, startDate, endDate, companyId,channelId,siteId,modelId, memberId,pageNo,pageSize);
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



    @Resource
    private SRepairQuoteDao sRepairQuoteDao;
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
}
