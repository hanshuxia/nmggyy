package com.anchorcms.icloud.model.common;

import com.anchorcms.cms.model.main.Content;
import com.anchorcms.core.model.CmsUser;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author machao
 * @Date 2017/3/30 13:49
 * @return
 * 电子签章申请
 */
@Entity
@Table(name = "s_cfca_apply")
public class SCfcaApply implements Serializable {
    private static final long serialVersionUID = -6156163653898328627L;
    private Integer applyId;
    private String companyId;
    private String companyName;
    private String orgNo;
    private String icrNo;
    private String taxId;
    private String broughtNo;
    private String sealCon;
    private String creditCode;
    private String legalName;
    private String legalId;
    private String bank;
    private Date applyDt;
    private String addrProvince;
    private String addrCity;
    private String addrCounty;
    private String addrStreet;
    private String contact;
    private String mobile;
    private String email;
    private String state;
//    private Integer contentId;
    private String status;
    private String signNo;//签章编号
    private String signPwd;//签章密码
    private String backReason;//驳回原因

    private Date startDt;//签章开通时间
    private Date deadlineDt;//签章截止时间

    @Basic
    @Column(name = "START_DT")
    public Date getStartDt() {
        return startDt;
    }

    public void setStartDt(Date startDt) {
        this.startDt = startDt;
    }

    @Basic
    @Column(name = "DEADLINE_DT")
    public Date getDeadlineDt() {
        return deadlineDt;
    }

    public void setDeadlineDt(Date deadlineDt) {
        this.deadlineDt = deadlineDt;
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
    @Column(name = "SIGN_PWD")
    public String getSignPwd() {
        return signPwd;
    }

    public void setSignPwd(String signPwd) {
        this.signPwd = signPwd;
    }
    @Basic
    @Column(name = "SIGN_NO")
    public String getSignNo() {
        return signNo;
    }

    public void setSignNo(String signNo) {
        this.signNo = signNo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPLY_ID")
    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    @Basic
    @Column(name = "COMPANY_ID")
    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Basic
    @Column(name = "COMPANY_NAME")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Basic
    @Column(name = "ORG_NO")
    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    @Basic
    @Column(name = "ICR_NO")
    public String getIcrNo() {
        return icrNo;
    }

    public void setIcrNo(String icrNo) {
        this.icrNo = icrNo;
    }

    @Basic
    @Column(name = "TAX_ID")
    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    @Basic
    @Column(name = "BROUGHT_NO")
    public String getBroughtNo() {
        return broughtNo;
    }

    public void setBroughtNo(String broughtNo) {
        this.broughtNo = broughtNo;
    }

    @Basic
    @Column(name = "SEAL_CON")
    public String getSealCon() {
        return sealCon;
    }

    public void setSealCon(String sealCon) {
        this.sealCon = sealCon;
    }

    @Basic
    @Column(name = "CREDIT_CODE")
    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    @Basic
    @Column(name = "LEGAL_NAME")
    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    @Basic
    @Column(name = "LEGAL_ID")
    public String getLegalId() {
        return legalId;
    }

    public void setLegalId(String legalId) {
        this.legalId = legalId;
    }

    @Basic
    @Column(name = "BANK")
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    @Basic
    @Column(name = "APPLY_DT")
    public Date getApplyDt() {
        return applyDt;
    }

    public void setApplyDt(Date applyDt) {
        this.applyDt = applyDt;
    }

    @Basic
    @Column(name = "ADDR_PROVINCE")
    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }

    @Basic
    @Column(name = "ADDR_CITY")
    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    @Basic
    @Column(name = "ADDR_COUNTY")
    public String getAddrCounty() {
        return addrCounty;
    }

    public void setAddrCounty(String addrCounty) {
        this.addrCounty = addrCounty;
    }

    @Basic
    @Column(name = "ADDR_STREET")
    public String getAddrStreet() {
        return addrStreet;
    }

    public void setAddrStreet(String addrStreet) {
        this.addrStreet = addrStreet;
    }

    @Basic
    @Column(name = "CONTACT")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Basic
    @Column(name = "MOBILE")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Basic
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "STATE")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

//    @Basic
//    @Column(name = "CONTENT_ID")
//    public Integer getContentId() {
//        return contentId;
//    }
//
//    public void setContentId(Integer contentId) {
//        this.contentId = contentId;
//    }

    @Basic
    @Column(name = "STATUS")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SCfcaApply that = (SCfcaApply) o;

        if (applyId != null ? !applyId.equals(that.applyId) : that.applyId != null) return false;
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) return false;
        if (companyName != null ? !companyName.equals(that.companyName) : that.companyName != null) return false;
        if (orgNo != null ? !orgNo.equals(that.orgNo) : that.orgNo != null) return false;
        if (icrNo != null ? !icrNo.equals(that.icrNo) : that.icrNo != null) return false;
        if (taxId != null ? !taxId.equals(that.taxId) : that.taxId != null) return false;
        if (broughtNo != null ? !broughtNo.equals(that.broughtNo) : that.broughtNo != null) return false;
        if (sealCon != null ? !sealCon.equals(that.sealCon) : that.sealCon != null) return false;
        if (creditCode != null ? !creditCode.equals(that.creditCode) : that.creditCode != null) return false;
        if (legalName != null ? !legalName.equals(that.legalName) : that.legalName != null) return false;
        if (legalId != null ? !legalId.equals(that.legalId) : that.legalId != null) return false;
        if (bank != null ? !bank.equals(that.bank) : that.bank != null) return false;
        if (applyDt != null ? !applyDt.equals(that.applyDt) : that.applyDt != null) return false;
        if (addrProvince != null ? !addrProvince.equals(that.addrProvince) : that.addrProvince != null) return false;
        if (addrCity != null ? !addrCity.equals(that.addrCity) : that.addrCity != null) return false;
        if (addrCounty != null ? !addrCounty.equals(that.addrCounty) : that.addrCounty != null) return false;
        if (addrStreet != null ? !addrStreet.equals(that.addrStreet) : that.addrStreet != null) return false;
        if (contact != null ? !contact.equals(that.contact) : that.contact != null) return false;
        if (mobile != null ? !mobile.equals(that.mobile) : that.mobile != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
//        if (contentId != null ? !contentId.equals(that.contentId) : that.contentId != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (backReason != null ? !backReason.equals(that.backReason) : that.backReason != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = applyId != null ? applyId.hashCode() : 0;
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        result = 31 * result + (orgNo != null ? orgNo.hashCode() : 0);
        result = 31 * result + (icrNo != null ? icrNo.hashCode() : 0);
        result = 31 * result + (taxId != null ? taxId.hashCode() : 0);
        result = 31 * result + (broughtNo != null ? broughtNo.hashCode() : 0);
        result = 31 * result + (sealCon != null ? sealCon.hashCode() : 0);
        result = 31 * result + (creditCode != null ? creditCode.hashCode() : 0);
        result = 31 * result + (legalName != null ? legalName.hashCode() : 0);
        result = 31 * result + (legalId != null ? legalId.hashCode() : 0);
        result = 31 * result + (bank != null ? bank.hashCode() : 0);
        result = 31 * result + (applyDt != null ? applyDt.hashCode() : 0);
        result = 31 * result + (addrProvince != null ? addrProvince.hashCode() : 0);
        result = 31 * result + (addrCity != null ? addrCity.hashCode() : 0);
        result = 31 * result + (addrCounty != null ? addrCounty.hashCode() : 0);
        result = 31 * result + (addrStreet != null ? addrStreet.hashCode() : 0);
        result = 31 * result + (contact != null ? contact.hashCode() : 0);
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
//        result = 31 * result + (contentId != null ? contentId.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (backReason != null ? backReason.hashCode() : 0);
        return result;
    }

    private Content content;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "content_id")
    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    /*private SIcloudPolicy policy;
    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "PAARENTAPPLY_ID")
    @NotFound(action= NotFoundAction.IGNORE)
    public SIcloudPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(SIcloudPolicy policy) {
        this.policy = policy;
    }
*/
    private CmsUser user;
    @OneToOne
    @JoinColumn(name = "CREATER_ID")
    public CmsUser getUser() {
        return user;
    }

    public void setUser(CmsUser user) {
        this.user = user;
    }

    private SCfcaPay sCfcaPay;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cfca_payid")
    @NotFound(action= NotFoundAction.IGNORE)
    public SCfcaPay getSCfcaPay() {
        return sCfcaPay;
    }
    public void setSCfcaPay(SCfcaPay sCfcaPay) {
        this.sCfcaPay = sCfcaPay;
    }
}
