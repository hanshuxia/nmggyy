package com.anchorcms.icloud.service.synergy;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.model.synergy.SAbilityInquiry;
import com.anchorcms.icloud.model.synergy.SDeviceInquiry;

import java.util.Date;

public interface SAbilityInquiryService {

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
    public Pagination getPageForMember(String statusType,CmsUser user, String inquiryTheme, Date startDate, Date endDate, String companyId,
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
     * @Desc 询价明细
     */
    public SAbilityInquiry byInquiryId(Integer inquiryId);

    /**
     * @Author zhouyq
     * @Date 2017/06/05
     * @Desc 设备询价明细
     */
    public SDeviceInquiry byDeviceInquiryId(Integer inquiryId);

    /**
     * 拉取询价信息的json，给前台管理界面报价用
     * @param inquiryId
     * @return
     */
    public String getInquiryObjJson(Integer inquiryId);

    /**
     * 拉取询价信息的json，给前台管理界面报价用
     * @param inquiryId
     * @return
     */
    public String getDeviceObjJson(Integer inquiryId);

    /**
     * 保存能力询价的报价
     * @param inquiryId
     * @param bj 报价
     * @return
     */
    public Integer saveAbilityInquiryBj(Integer inquiryId, Double bj);

    /**
     * 保存设备询价的报价
     * @param inquiryId
     * @param bj 报价
     * @return
     */
    public Integer saveDeviceInquiryBj(Integer inquiryId, Double bj);

    /**
     * 对能力询价(的报价) 下单/关闭
     * @param isOrder 1下单 0关闭
     * @return
     */
    public Integer orderOrClose(Integer inquiryId, boolean isOrder);
    public Integer deviceOrderOrClose(Integer inquiryId, boolean isOrder);
    /**
     * @Author 闫浩芮
     * @Date 2017/1/18 0018 11:07
     * 删除Inquiry
     */
    public SAbilityInquiry delete(Integer inquiryId);
    public SDeviceInquiry deleteDevice(Integer inquiryId);

}
