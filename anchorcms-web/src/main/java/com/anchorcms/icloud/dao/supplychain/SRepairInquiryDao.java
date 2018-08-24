package com.anchorcms.icloud.dao.supplychain;


import com.anchorcms.common.page.Pagination;

import com.anchorcms.icloud.model.payment.SSupplychainOrder;
import com.anchorcms.icloud.model.supplychain.SRepairInquiry;
import com.anchorcms.icloud.model.synergy.SDeviceInquiry;


import java.sql.Date;
import java.util.List;

/**
 * Created by hansx on 2016/12/27.
 * <p>
 * 维修资源询价DAO
 */
public interface SRepairInquiryDao {
    /**
     * @author hansx
     * @Date 2017/1/5 0005 下午 2:26
     * @return
     * 根据id获取询价信息
     */
    public SRepairInquiry findById(String id);

    /**
     * @author hansx
     * @Date 2017/1/5 0005 下午 2:26
     * @return
     * 添加询价信息
     */
    public SRepairInquiry save(SRepairInquiry srr);

    /**
     * @author hansx
     * @Date 2017/1/5 0005 下午 2:26
     * @return
     * 添加询价信息
     */
    public SDeviceInquiry save(SDeviceInquiry srr);

    /**
     * @author hansx
     * @Date 2017/1/5 0005 下午 2:27
     * @return
     * 获取列表
     */
    public List<SRepairInquiry> getList();

    /**
     * @author hansx
     * @Date 2017/1/5 0005 下午 2:27
     * @return
     * 条件查询，分页
     */
    public Pagination getInquiryListForMember(String statusType,String inquiryTheme, Date startDate, Date endDate,String companyName,String companyId,
                                              Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize,Boolean flag);

/**
     * @author hansx
     * @Date 2017/1/7 0007 下午 12:11
     * @return
     * 根据id更新报价
     */
    public int updateById(String id,Double offer, String statusType);

    /**
     * @author hansx
     * @Date 2017/1/7 0007 下午 12:11
     * @return
     * 根据id更新状态
     */
    public int updateStatusById(String id, String statusType);

    /**
     * @author dongxuecheng
     * @Date 2017/2/22 10:07
     * @return
     * @description评价
     */
    public Integer setEvaluteValue(String inquiryId,String evaluteValue);
    SSupplychainOrder save(SSupplychainOrder buy);
}
