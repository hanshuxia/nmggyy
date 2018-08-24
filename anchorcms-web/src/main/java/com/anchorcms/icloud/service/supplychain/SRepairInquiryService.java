package com.anchorcms.icloud.service.supplychain;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.cms.model.main.ContentExt;
import com.anchorcms.cms.model.main.ContentTxt;
import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.payment.SSupplychainOrder;
import com.anchorcms.icloud.model.supplychain.SRepairInquiry;
import com.anchorcms.icloud.model.synergy.SDeviceInquiry;

import java.sql.Date;
import java.util.List;

/**
 * Created by hansx on 2016/12/27.
 * <p>
 * 维修资源询价业务接口
 */
public interface SRepairInquiryService {

    /**
     * @return 根据id查询
     * @author hansx
     * @Date 2017/1/4 0004 下午 4:05
     */
    public SRepairInquiry findById(String id);

    /**
     * @return 保存
     * @author hansx
     * @Date 2017/2/13 0013 下午 3:10
     */
    public SRepairInquiry save(SRepairInquiry sReapirInquiry);

    /**
     * @return 保存
     * @author hansx
     * @Date 2017/1/4 0004 下午 4:05
     */
    Content save(SRepairInquiry srr, Content c, ContentExt ext, ContentTxt t, Integer channelId, Integer typeId, CmsUser user, boolean forMember, String[] attachmentPaths, String[] attachmentNames,
                 String[] attachmentFilenames);

    /**
     * @return 设备询价信息保存
     * @author hansx
     * @Date 2017/6/5 0004 下午 4:05
     */
    Content save(SDeviceInquiry srr, Content c, ContentExt ext, ContentTxt t, Integer channelId, Integer typeId, CmsUser user, boolean forMember, String[] attachmentPaths, String[] attachmentNames,
                 String[] attachmentFilenames);


    /**
     * @return 获取列表
     * @author hansx
     * @Date 2017/1/4 0004 下午 4:05
     */
    public List<SRepairInquiry> getList();

    /**
     * @return 待报价管理列表
     * @author hansx
     * @Date 2017/1/4 0004 下午 2:00
     */
    public Pagination getInquiryListForMember(String statusType, String inquiryTheme, Date startDate, Date endDate, String companyName, String companyId,
                                              Integer channelId, Integer siteId, Integer modelId, Integer memberId, int pageNo, int pageSize, Boolean flag);

    /**
     * @return 更新报价
     * @author hansx
     * @Date 2017/1/7 0007 下午 12:09
     */
    public int updateById(String id, Double offer, String statusType);

    /**
     * @return 更新状态
     * @author hansx
     * @Date 2017/1/7 0007 下午 12:09
     */
    public int updateStatusById(String id, String statusType);

    /**
     * @author dongxuecheng
     * @Date 2017/2/22 10:04
     * @return
     * @description评价赋值
     */
    public Integer setEvaluteValue(String inquiryId, String evaluteValue);
    void saveOrder(SSupplychainOrder buy);
}
