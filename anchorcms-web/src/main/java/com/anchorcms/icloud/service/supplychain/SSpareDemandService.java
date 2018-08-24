package com.anchorcms.icloud.service.supplychain;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.supplychain.SRepairDemand;
import com.anchorcms.icloud.model.supplychain.SRepairQuote;
import com.anchorcms.icloud.model.supplychain.SRepairRes;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author gengwenlong
 * @Date 2017/1/4 18:59
 * @return
 */
@Service
public interface SSpareDemandService {
    /**
     * @author gengwenlong
     * @Date 2017/1/4 18:59
     * @return报价
     */
    public Content save(SRepairQuote srq,String repairId,  String contentId, HttpServletRequest request,String[] attachmentPaths, String[] attachmentNames,
                        String[] attachmentFilenames, String[] picPaths, String[] picDescs, Content c, ContentExt ext, ContentTxt t, String nextUrl, Integer modelId,
                        Integer channelId, String textarea1, CmsUser user, Short charge, Integer typeId, boolean forMember,
                        HttpServletResponse response, ModelMap model);
    /**
     * @author: gengwenlong
     * @desciption 2017/1/9 保存报价+报价对象list
     */
    SRepairQuote save(SRepairQuote sRepairQuote, String repairQuoteObjsJsonStr);
    /**
     * @author gengwenlong
     * @Date 2017/1/9 10:24
     * 报价检索
     * @return
     */
    public List<SRepairDemand> getQuoteSearch(String repairId);
    /**
     * @author gengwenlong
     * @Date 2017/1/4 19:01
     * @return金牌老师傅详情
     */
    public List<SRepairRes> getSearch(String repairId);
    /**
     * @author gengwenlong
     * @Date 2017/1/4 19:02
     * @return金牌老师傅列表显示分页
     */
    public Pagination getPageForMember(Integer channelId, Integer siteId, Integer modelId, Integer UserId, Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme, String status, String payType, String statusType);
    /**
     * @author gengwenlong
     * @Date 2017/2/13 14:58
     * @return
     * 获取金牌老师傅相似能力的自定义标签
     */
    public List<SRepairRes> getList(Integer count, Integer orderBy, Integer listType, String repairType);
    /**
     * @author gengwenlong
     * @Date 2017/2/21 9:47
     * @return
     * 判断公司是否已对某维修需求报价
     */
    Boolean hasQuoted(String demandId, String companyId);
}
