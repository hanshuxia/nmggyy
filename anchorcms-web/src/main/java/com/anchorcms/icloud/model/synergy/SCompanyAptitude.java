package com.anchorcms.icloud.model.synergy;

import com.anchorcms.cms.model.main.Content;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

/**
 * @Author zhouyq
 * @Date 2017/05/10
 * @Desc 企业资质表
 */
@Entity
@Table(name = "s_company_aptitude")
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE )
public class SCompanyAptitude implements Serializable {
    private static final long serialVersionUID = -6045091367224122961L;
    private String aptitudeId; // 资质id
//    private String companyId; // 企业id
    private String isThreeToOne; // 是否三证合一
    private String businessRegNo; // 工商注册号
    private String orgInstitutionCode; // 组织机构代码
    private String taxRegNo; // 纳税人识别号
    private String legRepresentName; // 法人代表姓名
    private String legRepresentIdNO; // 法人身份证号
    private String legRepresentPhoneNO; // 法人手机号
    private String applyContact; // 申请联系人
    private String applyContactPhoneNO; // 联系人手机号
    private String createrId; // 创建人
    private Date createDt; // 创建日期
    private Date updateDt; // 更新日期
    private String status; // 资质信息状态
    private String backReason; // 资质驳回原因
    private String param; // 图片
    private String param1; // 图片1
    private String param2; // 图片2
    private String param3; // 图片3
    private String param4; // 图片4

    @Id
    @Column(name = "APTITUDE_ID")
    public String getAptitudeId() {
        return aptitudeId;
    }

    public void setAptitudeId(String aptitudeId) {
        this.aptitudeId = aptitudeId;
    }

//    @Basic
//    @Column(name = "COMPANY_ID")
//    public String getCompanyId() {
//        return companyId;
//    }
//
//    public void setCompanyId(String companyId) {
//        this.companyId = companyId;
//    }

    @Basic
    @Column(name = "IS_THREETOONE")
    public String getIsThreeToOne() {
        return isThreeToOne;
    }

    public void setIsThreeToOne(String isThreeToOne) {
        this.isThreeToOne = isThreeToOne;
    }

    @Basic
    @Column(name = "BUSINESSREG_NO")
    public String getBusinessRegNo() {
        return businessRegNo;
    }

    public void setBusinessRegNo(String businessRegNo) {
        this.businessRegNo = businessRegNo;
    }

    @Basic
    @Column(name = "ORGINSTITUTION_CODE")
    public String getOrgInstitutionCode() {
        return orgInstitutionCode;
    }

    public void setOrgInstitutionCode(String orgInstitutionCode) {
        this.orgInstitutionCode = orgInstitutionCode;
    }

    @Basic
    @Column(name = "TAXREG_NO")
    public String getTaxRegNo() {
        return taxRegNo;
    }

    public void setTaxRegNo(String taxRegNo) {
        this.taxRegNo = taxRegNo;
    }

    @Basic
    @Column(name = "LEGREPRESENT_NAME")
    public String getLegRepresentName() {
        return legRepresentName;
    }

    public void setLegRepresentName(String legRepresentName) {
        this.legRepresentName = legRepresentName;
    }

    @Basic
    @Column(name = "LEGREPRESENTId_NO")
    public String getLegRepresentIdNO() {
        return legRepresentIdNO;
    }

    public void setLegRepresentIdNO(String legRepresentIdNO) {
        this.legRepresentIdNO = legRepresentIdNO;
    }

    @Basic
    @Column(name = "LEGREPRESENT_PHONENO")
    public String getLegRepresentPhoneNO() {
        return legRepresentPhoneNO;
    }

    public void setLegRepresentPhoneNO(String legRepresentPhoneNO) {
        this.legRepresentPhoneNO = legRepresentPhoneNO;
    }

    @Basic
    @Column(name = "APPLY_CONTACT")
    public String getApplyContact() {
        return applyContact;
    }

    public void setApplyContact(String applyContact) {
        this.applyContact = applyContact;
    }

    @Basic
    @Column(name = "APPLYCONTACT_PHONENO")
    public String getApplyContactPhoneNO() {
        return applyContactPhoneNO;
    }

    public void setApplyContactPhoneNO(String applyContactPhoneNO) {
        this.applyContactPhoneNO = applyContactPhoneNO;
    }

    @Basic
    @Column(name = "CREATER_ID")
    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    @Basic
    @Column(name = "CREATE_DT")
    public Date getCreateDt() {
        return createDt;
    }

    public void setCreateDt(Date createDt) {
        this.createDt = createDt;
    }

    @Basic
    @Column(name = "UPDATE_DT")
    public Date getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt) {
        this.updateDt = updateDt;
    }

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "BACKREASON")
    public String getBackReason() {
        return backReason;
    }

    public void setBackReason(String backReason) {
        this.backReason = backReason;
    }

    @Basic
    @Column(name = "PARAM")
    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Basic
    @Column(name = "PARAM1")
    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    @Basic
    @Column(name = "PARAM2")
    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }

    @Basic
    @Column(name = "PARAM3")
    public String getParam3() {
        return param3;
    }

    public void setParam3(String param3) {
        this.param3 = param3;
    }

    @Basic
    @Column(name = "PARAM4")
    public String getParam4() {
        return param4;
    }

    public void setParam4(String param4) {
        this.param4 = param4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SCompanyAptitude that = (SCompanyAptitude) o;

        if (!aptitudeId.equals(that.aptitudeId)) return false;
        if (!isThreeToOne.equals(that.isThreeToOne)) return false;
        if (!businessRegNo.equals(that.businessRegNo)) return false;
        if (!orgInstitutionCode.equals(that.orgInstitutionCode)) return false;
        if (!taxRegNo.equals(that.taxRegNo)) return false;
        if (!legRepresentName.equals(that.legRepresentName)) return false;
        if (!legRepresentIdNO.equals(that.legRepresentIdNO)) return false;
        if (!legRepresentPhoneNO.equals(that.legRepresentPhoneNO)) return false;
        if (!applyContact.equals(that.applyContact)) return false;
        if (!applyContactPhoneNO.equals(that.applyContactPhoneNO)) return false;
        if (!createrId.equals(that.createrId)) return false;
        if (!createDt.equals(that.createDt)) return false;
        if (!updateDt.equals(that.updateDt)) return false;
        if (!status.equals(that.status)) return false;
        if (!backReason.equals(that.backReason)) return false;
        if (!param.equals(that.param)) return false;
        if (!param1.equals(that.param1)) return false;
        if (!param2.equals(that.param2)) return false;
        if (!param3.equals(that.param3)) return false;
        if (!param4.equals(that.param4)) return false;
//        return content.equals(that.content);
        return true;
    }

    @Override
    public int hashCode() {
        int result = aptitudeId != null ? aptitudeId.hashCode() : 0;
        result = 31 * result + (isThreeToOne != null ? isThreeToOne.hashCode() : 0);
        result = 31 * result + (businessRegNo != null ? businessRegNo.hashCode() : 0);
        result = 31 * result + (orgInstitutionCode != null ? orgInstitutionCode.hashCode() : 0);
        result = 31 * result + (taxRegNo != null ? taxRegNo.hashCode() : 0);
        result = 31 * result + (legRepresentName != null ? legRepresentName.hashCode() : 0);
        result = 31 * result + (legRepresentIdNO != null ? legRepresentIdNO.hashCode() : 0);
        result = 31 * result + (legRepresentPhoneNO != null ? legRepresentPhoneNO.hashCode() : 0);
        result = 31 * result + (applyContact != null ? applyContact.hashCode() : 0);
        result = 31 * result + (applyContactPhoneNO != null ? applyContactPhoneNO.hashCode() : 0);
        result = 31 * result + (createrId != null ? createrId.hashCode() : 0);
        result = 31 * result + (createDt != null ? createDt.hashCode() : 0);
        result = 31 * result + (updateDt != null ? updateDt.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (backReason != null ? backReason.hashCode() : 0);
        result = 31 * result + (param != null ? param.hashCode() : 0);
        result = 31 * result + (param1 != null ? param1.hashCode() : 0);
        result = 31 * result + (param2 != null ? param2.hashCode() : 0);
        result = 31 * result + (param3 != null ? param3.hashCode() : 0);
        result = 31 * result + (param4 != null ? param4.hashCode() : 0);
//        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

//    private Content content;
//
//    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "CONTENT_ID")
//    public Content getContent() {
//        return content;
//    }
//
//    public void setContent(Content content) {
//        this.content = content;
//    }

    private SCompany company;

    @ManyToOne(targetEntity = SCompany.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "COMPANY_ID")
    public SCompany getCompany() { return company; }

    public void setCompany(SCompany company) { this.company = company; }
}
