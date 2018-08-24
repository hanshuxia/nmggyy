package com.anchorcms.icloud.dao.supplychain;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.icloud.model.supplychain.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ${耿文龙} on 2016/12/21.
 */
public interface SSpareDemandDao {
/**
 * @author gengwenlong
 * @Date 2017/1/4 18:54
 * @return报价
 */
    public SRepairQuote save(SRepairQuote srq);
    /**
     * @author gengwenlong
     * @Date 2017/1/9 10:27
     * 报价检索
     * @return
     */
    public List<SRepairDemand> getQuoteSearch(String repairId);
    /**
     * @author gengwenlong
     * @Date 2017/1/4 18:55
     * @return金牌老师傅列表
     */
    Pagination getAllSearch(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title,
                            Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);

    /**
     * @author gengwenlong
     * @Date 2017/1/4 18:56
     * @return金牌老师傅查看详情
     */
    List<SRepairRes> getSearch(String repairId);
    /**
     * @author gengwenlong
     * @Date 2017/1/4 18:56
     * @return金牌老师傅列表分页
     */
    public Pagination getPageBySelf(Integer channelId, Integer siteId, Integer modelId,
                                    Integer memberId, int pageNo, int pageSize, Date releaseDt, Date deadlineDt, Integer demandId, String inquiryTheme,
                                    Integer UserId, String status, String payType, String statusType);


    /**
     * @author 苏和 13739980760
     * @Date 2017/1/23 17:12
     * @return
     * 备品备件求购列表展示检索
     */
    Pagination getPageBySiteIdBpbjqgById(Integer[] siteIds, Integer[] typeIds, Boolean titleImg, Boolean recommend, String title, Map<String, String[]> attr, int orderBy, int pageNo, int pageSize, String[] params);
    /**
     * @author gengwenlong
     * @Date 2017/2/13 15:01
     * @return
     * 获取金牌老师傅相似能力的自定义标签
     */
    public List<SRepairRes> getList(Integer count, Integer orderBy, Integer listType, String repairType);
    /**
     * @author gengwenlong
     * @Date 2017/2/21 9:49
     * @return
     * 判断公司是否已对某维修需求报价
     */
    Boolean hasQuoted(String demandId, String companyId);
}
