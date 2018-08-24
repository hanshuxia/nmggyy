package com.anchorcms.icloud.service.synergy.impl;

import com.anchorcms.common.page.Pagination;
import com.anchorcms.core.model.CmsUser;
import com.anchorcms.icloud.dao.synergy.SAbilityInquiryDao;
import com.anchorcms.icloud.model.synergy.SAbilityInquiry;
import com.anchorcms.icloud.model.synergy.SDeviceInquiry;
import com.anchorcms.icloud.service.synergy.SAbilityInquiryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
@Transactional
public class SAbilityInquiryServiceImpl implements SAbilityInquiryService {

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
                                       int pageNo, int pageSize) {
        return dao.getPageBySelf(statusType,user, inquiryTheme, startDate, endDate, companyId, pageNo,pageSize);
    }

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
                                       int pageNo, int pageSize) {
        return dao.getPageForDevice(statusType, user, inquiryTheme, startDate, endDate, companyId, pageNo, pageSize);
    }

    /**
     * @Author maocheng
     * @Email netuser.orz@icloud.com
     * @Date 2016/12/29
     * @Desc 询价明细
     */
    public SAbilityInquiry byInquiryId(Integer inquiryId){return dao.byInquiryId(inquiryId);}


    /**
     * @Author zhouyq
     * @Date 2017/06/05
     * @Desc 设备询价明细
     */
    public SDeviceInquiry byDeviceInquiryId(Integer inquiryId){return dao.byDeviceInquiryId(inquiryId);}

    /**
     * 拉取 能力询价信息 的json，给前台管理界面 报价 用
     * @param inquiryId
     * @return
     */
    public String getInquiryObjJson(Integer inquiryId) {
        SAbilityInquiry aInq = byInquiryId(inquiryId);
        StringBuffer json = new StringBuffer();
        if(aInq!=null){
            java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            json.append("{\"aInquiryObj\":{");
            json.append("\"aInquiryObjId\":\"").append(nf.format(aInq.getInquiryId())).append("\",");
            json.append("\"abilityName\":\"").append(aInq.getAbility().getAbilityName()).append("\",");
            json.append("\"abilityCode\":\"").append(aInq.getAbility().getAbilityCode()).append("\",");
            json.append("\"inquiryCompany\":\"").append(aInq.getCompany().getCompanyName()).append("\",");
            json.append("\"amount\":\"").append(nf.format(aInq.getDemandCount())).append("\",");
            json.append("\"unit\":\"").append(aInq.getAbility().getUnit()).append("\",");
            json.append("\"deliverDt\":\"").append(aInq.getDeliverDt()).append("\",");
            json.append("\"expPrice\":\"").append(nf.format(aInq.getExpectPrice())).append("\"");
            json.append("}}");
        }
        return json.toString();
    }

    /**
     * 拉取 能力询价信息 的json，给前台管理界面 报价 用
     * @param inquiryId
     * @return
     */
    public String getDeviceObjJson(Integer inquiryId) {
        SDeviceInquiry aInq = byDeviceInquiryId(inquiryId);
        StringBuffer json = new StringBuffer();
        if(aInq!=null){
            java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
            nf.setGroupingUsed(false);
            json.append("{\"aInquiryObj\":{");
            json.append("\"aInquiryObjId\":\"").append(nf.format(aInq.getInquiryId())).append("\",");
            json.append("\"abilityName\":\"").append(aInq.getScompanyDevice().getDeviceName()).append("\",");
            json.append("\"abilityCode\":\"").append(aInq.getScompanyDevice().getDeviceCode()).append("\",");
            json.append("\"inquiryCompany\":\"").append(aInq.getCompany().getCompanyName()).append("\",");
            json.append("\"amount\":\"").append(nf.format(aInq.getDemandCount())).append("\",");
            json.append("\"unit\":\"").append(aInq.getScompanyDevice().getUnit()).append("\",");
            json.append("\"expPrice\":\"").append(nf.format(aInq.getExpectPrice())).append("\"");
            json.append("}}");
        }
        return json.toString();
    }

    /**
     * 保存能力询价的报价
     *
     * @param inquiryId
     * @param bj        报价
     * @return
     */
    public Integer saveAbilityInquiryBj(Integer inquiryId, Double bj) {
        return dao.saveAbilityInquiryBj(inquiryId,bj);
    }

    /**
     * 保存设备询价的报价
     *
     * @param inquiryId
     * @param bj 报价
     * @return
     */
    public Integer saveDeviceInquiryBj(Integer inquiryId, Double bj) {
        return dao.saveDeviceInquiryBj(inquiryId,bj);
    }

    /**
     * 对能力询价(的报价) 下单/关闭
     *
     * @param inquiryId
     * @param isOrder   1下单 0关闭
     * @return
     */
    public Integer orderOrClose(Integer inquiryId, boolean isOrder) {
        String statusType;
        if(isOrder){
            statusType = "2";
        }else {
            statusType="3";
        }
        return dao.changeStatusType(inquiryId,statusType);
    }
    public Integer deviceOrderOrClose(Integer inquiryId, boolean isOrder) {
        String statusType;
        if(isOrder){
            statusType = "2";
        }else {
            statusType="3";
        }
        return dao.changeDeviceStatusType(inquiryId,statusType);
    }

    public SAbilityInquiry delete(Integer inquiryId) {
        SAbilityInquiry abilityInquiry =dao.byInquiryId(inquiryId);
        abilityInquiry =dao.delete(abilityInquiry);
        return abilityInquiry;
    }
    public SDeviceInquiry deleteDevice(Integer inquiryId) {
        SDeviceInquiry deviceInquiry =dao.byDeviceInquiryId(inquiryId);
        deviceInquiry =dao.deleteDevice(deviceInquiry);
        return deviceInquiry;
    }
    @Resource
    private SAbilityInquiryDao dao;

}
