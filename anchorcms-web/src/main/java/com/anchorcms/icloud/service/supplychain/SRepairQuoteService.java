package com.anchorcms.icloud.service.supplychain;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.supplychain.SRepairQuote;

import java.util.Date;
import java.util.List;

/**
 * Created by hansx on 2016/12/27.
 *
 * 维修资源报价业务接口
 */
public interface SRepairQuoteService {

    /**
     * @author hansx
     * @Date 2017/1/4 0004 下午 4:05
     * @return
     * 根据id查询
     */
    public SRepairQuote findById(String id);
    /**
     * @author hansx
     * @Date 2017/1/4 0004 下午 4:05
     * @return
     * 保存
     */
    Content save(SRepairQuote srr, Content c, ContentExt ext, ContentTxt t, Integer channelId, Integer typeId, CmsUser user, boolean forMember);

   /**
    * @author hansx
    * @Date 2017/1/4 0004 下午 4:05
    * @return
    * 获取列表
    */
    public List<SRepairQuote> getList();

    /**
     * @author hansx
     * @Date 2017/1/4 0004 下午 2:00
     * @return
     * 待报价管理列表
     */
    public Pagination getInquiryListForMember(String inquiryTheme, Date startDate, Date endDate, String companyId,
                                              Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize);
}
