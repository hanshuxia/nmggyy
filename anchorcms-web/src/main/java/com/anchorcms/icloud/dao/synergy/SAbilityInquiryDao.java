package com.anchorcms.icloud.dao.synergy;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.synergy.SAbilityInquiry;
import com.anchorcms.icloud.model.synergy.SDeviceInquiry;


import java.util.Date;

public interface SAbilityInquiryDao {

    /**
     * 协同制造-能力方-待报价方案列表
     * @param statusType
     * @param user
     * @param inquiryTheme 询价主题
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param companyId 询价企业
     * @param pageNo
     * @param pageSize
     * @Author maocheng
     * @return
     */

    public Pagination getPageBySelf(String statusType,CmsUser user, String inquiryTheme, Date startDate, Date endDate, String companyId,
                                    int pageNo, int pageSize);

    /**
     * 协同制造-企业设备-待报价方案列表
     * @param statusType
     * @param user
     * @param inquiryTheme 询价主题
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param companyId 询价企业
     * @param pageNo
     * @param pageSize
     * @Author zhouyq
     * @return
     */

    public Pagination getPageForDevice(String statusType, CmsUser user, String inquiryTheme, Date startDate, Date endDate, String companyId,
                                    int pageNo, int pageSize);

    /**
     * @Author maocheng
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/29
     * @Desc 通过ID获取 能力询价对象 信息
     */
    public SAbilityInquiry byInquiryId(Integer inquiryId);



    /**
     * @Author zhouyq
     * @Date 2017/06/05
     * @Desc 设备询价明细
     */
    public SDeviceInquiry byDeviceInquiryId(Integer inquiryId);

    /**
     * 根据能力询价表id 更改状态位
     * @param inquiryId
     * @param statusType
     * @return
     */
    Integer changeStatusType(Integer inquiryId, String statusType);
    Integer changeDeviceStatusType(Integer inquiryId, String statusType);
    /**
     * 保存能力询价的报价
     *
     * @param inquiryId
     * @param bj        报价
     * @return
     */
    Integer saveAbilityInquiryBj(Integer inquiryId, Double bj);

    /**
     * 保存设备询价的报价
     *
     * @param inquiryId
     * @param bj 报价
     * @return
     */
    Integer saveDeviceInquiryBj(Integer inquiryId, Double bj);

    /**
     * @Author 闫浩芮
     * @Date 2017/1/18 0018 11:08
     * 删除
     */
    public SAbilityInquiry delete(SAbilityInquiry abilityINduiry);
    public SDeviceInquiry deleteDevice(SDeviceInquiry deviceInquiry);

}
