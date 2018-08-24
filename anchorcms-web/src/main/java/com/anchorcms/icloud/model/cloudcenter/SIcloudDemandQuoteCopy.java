package com.anchorcms.icloud.model.cloudcenter;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Author 阁楼麻雀
 * @Email netuser.orz@icloud.com
 * @Date 2017/1/3
 * @Desc 云资源需求报价表-json专用复制类
 */
public class SIcloudDemandQuoteCopy implements Serializable{
    private String demandObjId;
    private String demand_obj_id;
    private String demandId;
    private String demandName;
    private String count;
    private String unit;
    private String classCode;
    private String expertPrice;
    private String offerExplan;
    private String price;
    private String contact;
    private String telephone;
    private String mobile;
    private String email;
    private String fax;
    private String operatorId;
    private Date updateDt;
    private String createrId;
    private Date releaseDt;
    private Date deadlineDt;
    private Date createDt;
    private String deFlag;
    private String companyId;
    private String companyName;
    private String selectedStatus;

    public String getDemand_obj_id() {
        return demand_obj_id;
    }

    public void setDemand_obj_id(String demand_obj_id) {
        this.demand_obj_id = demand_obj_id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getDemandName() {
        return demandName;
    }

    public void setDemandName(String demandName) {
        this.demandName = demandName;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getExpertPrice() {
        return expertPrice;
    }

    public void setExpertPrice(String expertPrice) {
        this.expertPrice = expertPrice;
    }

    public String getDemandObjId() {
        return demandObjId;
    }

    public void setDemandObjId(String demandObjId) {
        this.demandObjId = demandObjId;
    }

    public String getDemandId() {
        return demandId;
    }

    public void setDemandId(String demandId) {
        this.demandId = demandId;
    }

    public String getOfferExplan() {
        return offerExplan;
    }

    public void setOfferExplan(String offerExplan) {
        this.offerExplan = offerExplan;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public Date getReleaseDt() {
        return releaseDt;
    }

    public void setReleaseDt(Date releaseDt) {
        this.releaseDt = releaseDt;
    }

    public Date getDeadlineDt() {
        return deadlineDt;
    }

    public void setDeadlineDt(Date deadlineDt) {
        this.deadlineDt = deadlineDt;
    }

    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    public String getDeFlag() {
        return deFlag;
    }

    public void setDeFlag(String deFlag) {
        this.deFlag = deFlag;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getSelectedStatus() {
        return selectedStatus;
    }

    public void setSelectedStatus(String selectedStatus) {
        this.selectedStatus = selectedStatus;
    }

    @Override
    public String toString() {
        return "SIcloudDemandQuoteCopy{" +
                "demandObjId=" + demandObjId +
                ", demandId=" + demandId +
                ", classCode=" + classCode +
                ", offerExplan='" + offerExplan + '\'' +
                ", price=" + price +
                ", expectPrice=" + expertPrice +
                ", contact='" + contact + '\'' +
                ", telephone='" + telephone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", fax='" + fax + '\'' +
                ", operatorId='" + operatorId + '\'' +
                ", updateDt=" + updateDt +
                ", createrId='" + createrId + '\'' +
                ", releaseDt=" + releaseDt +
                ", deadlineDt=" + deadlineDt +
                ", createDt=" + createDt +
                ", deFlag='" + deFlag + '\'' +
                ", companyId='" + companyId + '\'' +
                ", selectedStatus='" + selectedStatus + '\'' +
                '}';
    }
}
